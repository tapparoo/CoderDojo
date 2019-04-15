package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.Location;
import com.skilldistillery.coderdojo.services.LocationService;
import com.skilldistillery.coderdojo.services.UserService;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
@RequestMapping("api")
public class LocationController {

	@Autowired
	private LocationService service;
	
	@Autowired
	private UserService uservice;

	//  GET Meetings
	@GetMapping("locations")
	public Set<Location> index(HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		return service.findAllLocation(principal.getName());
	}
}
