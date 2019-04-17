package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.UserGoal;
import com.skilldistillery.coderdojo.services.UserGoalService;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
@RequestMapping("api")
public class UserGoalController {

	@Autowired
	private UserGoalService service;
	
	@PutMapping("usergoals/{id}")
	public UserGoal putUserGoal(@PathVariable("id") Integer id, @RequestBody UserGoal userGoal, HttpServletResponse resp, Principal principal) {
		userGoal = service.update(id, userGoal);
		if(userGoal == null) {
			resp.setStatus(404);
		}
		return userGoal;
	}
}
