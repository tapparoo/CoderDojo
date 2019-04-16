package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.Role;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.services.RoleService;
import com.skilldistillery.coderdojo.services.UserService;

@RestController
@RequestMapping("api/roles")
@CrossOrigin({ "*", "http://localhost:4202" })
public class RoleController {
	
	@Autowired
	RoleService serv;
	@Autowired
	UserService userServ;
	
	@GetMapping
	public List<Role> getRoles(HttpServletResponse res, Principal principal){
		List<Role> roles = null;
		
		if (userServ.findByUsername(principal.getName()).isAdmin()) {
			roles = serv.index();
			res.setStatus(200);
		}else {
			res.setStatus(401);
		}
		return roles;
	}
	
	@GetMapping("{role}/users")
	public List<UserDetail> getUsersByRoleName(@PathVariable("role") String role, HttpServletResponse res,
			HttpServletRequest req, Principal principal) {

		User requestingUser = userServ.findByUsername(principal.getName());
		List<UserDetail> results = serv.findUsersByRole(role);

		if (results != null) {
			if (requestingUser.isAdmin()) {

				res.setStatus(200);
			}
		}

		return results;

	}

}
