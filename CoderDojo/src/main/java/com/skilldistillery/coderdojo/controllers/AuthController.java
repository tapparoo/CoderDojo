package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.services.AuthService;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
public class AuthController {
	@Autowired
	private AuthService serv;

//	@PostMapping("/register")
//	public User register(@RequestBody User user, HttpServletResponse resp) {
//		if (user == null) {
//			resp.setStatus(400);
//		}
//
//		user = serv.register(user);
//
//		return user;
//	}

	@GetMapping("/authenticate")
	public Principal authenticate(Principal principal) {
		return principal;
	}
}
