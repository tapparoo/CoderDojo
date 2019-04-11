package com.skilldistillery.coderdojo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
   @Autowired
   UserService serv;
   
   @GetMapping
   public List<User> getAllUsers(HttpServletResponse res, HttpServletRequest req){
	   List<User> users = serv.index();

		if (users == null || !(users.size() > 0)) {
			res.setStatus(204);
		} else {
			res.setStatus(200);
		}

		return users;
   }
}
