package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.Location;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.services.LocationService;
import com.skilldistillery.coderdojo.services.UserDetailsServiceImpl;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
@RequestMapping("api")
public class LocationController {

	@Autowired
	private LocationService service;
	
	@Autowired
	private UserDetailsServiceImpl udService;

	//  GET Meetings
	@GetMapping("locations")
	public Set<Location> index(HttpServletRequest req, HttpServletResponse res) {
		return service.findAllLocation();
	}
	
	@GetMapping("locations/{lid}/users")
	public List<UserDetail> findUsersByLocation(HttpServletResponse res,
			@PathVariable("lid") Integer lid,
			Principal principal) {
		return service.findUsersByLocation(lid);
	}
	
	@GetMapping("locations/{lid}/users/students")
	public List<UserDetail> findStudentsByLocation(HttpServletResponse res,
			@PathVariable("lid") Integer lid,
			Principal principal) {
		return service.findStudentsByLocation(lid);
	}
	
}
