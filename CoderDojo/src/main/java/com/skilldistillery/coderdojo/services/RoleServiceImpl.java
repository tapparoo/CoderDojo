package com.skilldistillery.coderdojo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Role;
import com.skilldistillery.coderdojo.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleRepository repo;
	
	@Override
	public Role findByName(String name) {
		return repo.findByName(name);
	}
}
