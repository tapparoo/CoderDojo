package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.services.UserService;

@CrossOrigin({"*", "http://localhost:4200"})
@RestController
public class UserController {

	@Autowired
	UserService serv;
	
	@RequestMapping(path="api/users", method=RequestMethod.GET)
	public List<User> index(HttpServletRequest req, HttpServletResponse res, Principal principal) {
		System.out.println(principal);
		return serv.index();
//	  return serv.index(principal.getName());
	}

}
