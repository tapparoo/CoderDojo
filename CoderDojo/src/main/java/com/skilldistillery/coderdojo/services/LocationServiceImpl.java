package com.skilldistillery.coderdojo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Location;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.repositories.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository repo;
	
	@Override
	public Set<Location> findAllLocation() {
		Set<Location> locations= null;
		locations = new HashSet<Location>(repo.findAll());
		return locations;
	}
	
	@Override
	public List<UserDetail> findUsersByLocation(int lid){
		return repo.findUserDetailsByLocationId(lid);
	}
	
	@Override
	public List<UserDetail> findStudentsByLocation(int lid){
		List<UserDetail> allUsersAtLocation = repo.findUserDetailsByLocationId(lid);
		List<UserDetail> filteredByStudentRole = new ArrayList<UserDetail>();
		
		for (UserDetail user : allUsersAtLocation) {
			if (user.getUser().isStudent()) {
				filteredByStudentRole.add(user);
			}
		}
		return filteredByStudentRole;
	}
}
