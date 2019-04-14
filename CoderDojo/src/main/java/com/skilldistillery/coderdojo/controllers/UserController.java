package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.Achievement;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.services.AchievementService;
import com.skilldistillery.coderdojo.services.AddressService;
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

	// admin
	@GetMapping
	public List<UserDetail> getAllUsers(HttpServletResponse res, HttpServletRequest req, Principal principal) {
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
			HttpServletRequest req, Principal principal) {
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
			HttpServletRequest req, Principal principal) {
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

	// Update USER object (username/password)
	@PutMapping("{id}")
	public User updateUser(@RequestBody User usr, HttpServletResponse res, HttpServletRequest req) {
		User user = serv.updateUser(usr);

		if (user != null) {
			res.setStatus(200);
		} else {
			res.setStatus(404);
		}

		return user;
	}

	// Update USER DETAILS
	@PutMapping
	public UserDetail updateUserDetails(@RequestBody UserDetail usr, HttpServletResponse res) {
		addrServ.update(usr.getAddress());
		UserDetail user = deets.update(usr);
		if (user != null) {
			res.setStatus(200);
		} else {
			res.setStatus(404);
		}

		return user;
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
			HttpServletResponse res, HttpServletRequest req) {
		UserDetail user = deets.findUserDetailByUsername(username);
		Achievement achievement = achieveServ.findAchievementById(aid);

		if (user != null && achievement != null) {
			user.removeAchievement(achievement);
			res.setStatus(200);
		} else {
			res.setStatus(404);
		}
	}

//   @DeleteMapping("{username}")
//   public User disableUser(@PathVariable("username") String username, HttpServletResponse res, HttpServletRequest req){
//	   User user = serv.disableUser(username);
//	   
//	   if (user != null) {
//		   res.setStatus(200);
//	   } else {
//		   res.setStatus(404);
//	   }
//	   
//	   return user;
//   }
}
