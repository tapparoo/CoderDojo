package com.skilldistillery.coderdojo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.services.UserDetailsServiceImpl;
import com.skilldistillery.coderdojo.services.UserService;

@RestController
@RequestMapping("api/users")
@CrossOrigin({ "*", "http://localhost:4202" })
public class UserController {
   @Autowired
   UserService serv;
   @Autowired
   UserDetailsServiceImpl deets;
   
   // admin
   @GetMapping
   public List<UserDetail> getAllUsers(HttpServletResponse res, HttpServletRequest req){
	   List<UserDetail> users = deets.index();

		if (users == null || !(users.size() > 0)) {
			res.setStatus(204);
		} else {
			res.setStatus(200);
		}

		return users;
   }
   
   @GetMapping("{id}")
   public UserDetail getUserDetailsById(@PathVariable("id") String id, HttpServletResponse res, HttpServletRequest req){
	   UserDetail user = null;
	   
	   // Search by id or username, depending on what was passed in
	   try {
		   int actualId = Integer.parseInt(id);
		   user = deets.findById(actualId);
	   } catch (Exception e) {
		   user = deets.findUserDetailByUsername(id);
	   }
	   
	   if (user != null) {
		   res.setStatus(200);
	   } else {
		   res.setStatus(404);
	   }
	   
	   return user;
   }
   
   // Update USER object (username/password)
   @PutMapping("{id}")
   public User updateUser(@RequestBody User usr, HttpServletResponse res, HttpServletRequest req){
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
   public UserDetail updateUserDetails(@RequestBody UserDetail usr, HttpServletResponse res){
	   UserDetail user = deets.update(usr);
	   
	   if (user != null) {
		   res.setStatus(200);
	   } else {
		   res.setStatus(404);
	   }
	   
	   return user;
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
