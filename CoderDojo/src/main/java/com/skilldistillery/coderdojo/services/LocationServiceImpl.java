package com.skilldistillery.coderdojo.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Location;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.LocationRepository;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository repo;
	
	@Autowired
	private UserRepository repoUser;

	@Override
	public Set<Location> findAllLocation() {
		Set<Location> locations= null;
		locations = new HashSet<Location>(repo.findAll());
		return locations;
	}
	
	
	
}
