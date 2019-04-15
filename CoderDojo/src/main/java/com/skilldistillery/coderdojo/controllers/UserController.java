package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.Achievement;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.services.AchievementService;
import com.skilldistillery.coderdojo.services.AddressService;
import com.skilldistillery.coderdojo.services.RoleService;
import com.skilldistillery.coderdojo.services.SecurityService;
import com.skilldistillery.coderdojo.services.UserDetailsServiceImpl;
import com.skilldistillery.coderdojo.services.UserService;

@RestController
@RequestMapping("api/users")
@CrossOrigin({ "*", "http://localhost:4202" })
public class UserController {
	@Autowired
	private UserService serv;
	@Autowired
	private UserDetailsServiceImpl deets;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private AddressService addrServ;
	@Autowired
	private AchievementService achieveServ;
	@Autowired
	private RoleService roleServ;

	// admin
	@GetMapping
	public List<UserDetail> getAllUsers(HttpServletResponse res, Principal principal) {
		// Only admins can get this
		if (!serv.findByUsername(principal.getName()).isAdmin()) {
			return null;
		}

		List<UserDetail> users = deets.index();

		if (users == null || !(users.size() > 0)) {
			res.setStatus(204);
		} else {
			res.setStatus(200);
		}

		return users;
	}

	@GetMapping("{username}")
	public UserDetail getUserDetailsByUsername(@PathVariable("username") String username, HttpServletResponse res,
			Principal principal) {
		UserDetail requestedUser = deets.findUserDetailByUsername(username);
		User requestingUser = serv.findByUsername(principal.getName());

		if (requestedUser != null) {
			// Only the owning user or an admin can see a user's profile
			if (requestingUser.isAdmin()
					|| requestingUser.getUsername().equalsIgnoreCase(requestedUser.getUser().getUsername())) {
				res.setStatus(200);
			} else {
				res.setStatus(401);
				return null;
			}
		} else {
			res.setStatus(404);
		}
		return requestedUser;
	}

	@GetMapping("{username}/achievements")
	public List<Achievement> getUserAchievements(@PathVariable("username") String username, HttpServletResponse res,
			Principal principal) {
		UserDetail requestedUser = deets.findUserDetailByUsername(username);
		User requestingUser = serv.findByUsername(principal.getName());
		List<Achievement> achievements = requestedUser.getAchievements();

		if (requestedUser != null) {
			// Only the owning user or an admin can see a user's profile
			if (requestingUser.isAdmin()
					|| requestingUser.getUsername().equalsIgnoreCase(requestedUser.getUser().getUsername())) {

				res.setStatus(200);
			}
		}

		return achievements;
	}

	@GetMapping("{username}/children")
	public Set<UserDetail> getChildren(@PathVariable("username") String username, HttpServletResponse res,
			Principal principal) {
		UserDetail requestedUser = deets.findUserDetailByUsername(username);
		User requestingUser = serv.findByUsername(principal.getName());
		Set<UserDetail> children = null;

		if (requestedUser != null) {
			children = requestedUser.getChildren();
			// Only the owning user or an admin can see a user's profile
			if (requestingUser.isAdmin()
					|| requestingUser.getUsername().equalsIgnoreCase(requestedUser.getUser().getUsername())) {

				res.setStatus(200);
			} else {
				res.setStatus(401);
			}
		} else {
			res.setStatus(404);
		}

		return children;
	}

	// Update USER object (username/password)
	@PutMapping("{id}")
	public User updateUser(@RequestBody User usr, Principal principal, HttpServletResponse res) {
		User requestedUser = serv.findByUsername(usr.getUsername());
		User requestingUser = serv.findByUsername(principal.getName());
		User user = null;
		
		if (requestedUser != null) {
			// Only the owning user or an admin can update a user's profile
			if (requestingUser.isAdmin()
					|| requestingUser.getUsername().equalsIgnoreCase(requestedUser.getUsername())) {
				
				serv.updateUser(usr);
				res.setStatus(200);
			} else {
				res.setStatus(401);
			}
		} else {
			res.setStatus(404);
		}
		
		return user;
	}

	// Update USER DETAILS
	@PutMapping
	public UserDetail updateUserDetails(@RequestBody UserDetail usr, Principal principal, HttpServletResponse res) {
		if (usr.getAddress() != null) {
			addrServ.update(usr.getAddress());
		}
		
		UserDetail requestedUser = deets.findUserDetailByUsername(usr.getUser().getUsername());
		User requestingUser = serv.findByUsername(principal.getName());
		if (requestedUser != null) {
			// Only the owning user, the parent, or an admin can update a user's profile
			if (requestingUser.isAdmin() || deets.findUserDetailByUsername(requestingUser.getUsername()).isParentOf(requestedUser)
					|| requestingUser.getUsername().equalsIgnoreCase(requestedUser.getUser().getUsername())) {
				
				deets.update(usr);
				res.setStatus(200);
			} else {
				res.setStatus(401);
			}
		} else {
			res.setStatus(404);
		}

		return requestedUser;
	}
	
	// Add child
	@PostMapping("{username}/children")
	public UserDetail addChild(@RequestBody UserDetail usr, Principal principal, HttpServletResponse res) {
		UserDetail child = deets.findUserDetailByUsername(usr.getUser().getUsername());
		User requestingUser = serv.findByUsername(principal.getName());
		
		if (child != null) {
			// Only the owning user, the parent, or an admin can update a user's profile
			if (requestingUser.isAdmin() || requestingUser.isParent()) {
				// Add Student role, remove default Parent role ( set in UserServiceImpl - save() )
				User childAuth = serv.findByUsername(child.getUser().getUsername());
				childAuth.removeRole(roleServ.findByName("PARENT"));
				childAuth.addRole(roleServ.findByName("STUDENT"));
				// Add child to parent that created it
				UserDetail parent = deets.findUserDetailByUsername(principal.getName());
				parent.addChild(child);
				deets.update(parent);
				res.setStatus(200);
			} else {
				res.setStatus(401);
			}
		} else {
			res.setStatus(404);
		}
		
		return child;
	}

	@PutMapping("{username}/achievements/{id}")
	public void addAchievement(@PathVariable("username") String username, @PathVariable("id") Integer aid,
			HttpServletResponse res, HttpServletRequest req) {
		UserDetail user = deets.findUserDetailByUsername(username);
		Achievement achievement = achieveServ.findAchievementById(aid);

		if (user != null && achievement != null) {
			user.addAchievement(achievement);
			res.setStatus(200);
		} else {
			res.setStatus(404);
		}
	}

	@DeleteMapping("{username}/achievements/{id}")
	public void removeAchievement(@PathVariable("username") String username, @PathVariable("id") Integer aid,
			HttpServletResponse res) {
		UserDetail user = deets.findUserDetailByUsername(username);
		Achievement achievement = achieveServ.findAchievementById(aid);

		if (user != null && achievement != null) {
			user.removeAchievement(achievement);
			res.setStatus(200);
		} else {
			res.setStatus(404);
		}
	}

		@GetMapping("roles/{role}")
		public List<UserDetail> getUsersByRoleName(@PathVariable("role") String role, HttpServletResponse res, HttpServletRequest req, Principal principal){
			
			User requestingUser = serv.findByUsername(principal.getName());
			List<UserDetail> results = deets.findUsersByRole(role);
			
			if (results != null) {
				if (requestingUser.isAdmin()) {

					res.setStatus(200);
				}
			}

		
			
			return results;
			
		}

}


