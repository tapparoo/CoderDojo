package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.services.SecurityService;
import com.skilldistillery.coderdojo.services.UserDetailsServiceImpl;
import com.skilldistillery.coderdojo.services.UserService;
import com.skilldistillery.coderdojo.validator.UserValidator;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceImpl deetsService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/register")
    public UserDetail registration(@RequestBody User user, HttpServletResponse resp) {
    	// check if username already exists
    	if (userService.findByUsername(user.getUsername()) != null) {
    		resp.setStatus(409);
    		return null;
    	}
    	
        userService.save(user);
        UserDetail newUser = deetsService.findUserDetailByUsername(user.getUsername());
        if (newUser != null) {
        	resp.setStatus(201);
        } else {
        	resp.setStatus(409);
        }
        return newUser;
    }

    // TODO: remove model/jsp stuff
    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
    
	@GetMapping("/authenticate")
	public Principal authenticate(Principal principal) {
		return principal;
	}
}
