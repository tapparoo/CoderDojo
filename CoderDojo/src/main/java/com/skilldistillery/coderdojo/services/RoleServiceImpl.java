package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Role;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.repositories.RoleRepository;
import com.skilldistillery.coderdojo.repositories.UserDetailRepository;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleRepository repo;
	
	@Autowired
	UserDetailRepository deetsRepo;
	
	@Override
	public Role findByName(String name) {
		return repo.findByName(name);
	}

	@Override
	public Set<UserDetail> findUsersByRole(String role){
		
		Set<UserDetail> results = deetsRepo.findUsersByRole(role);
//		for (UserDetail userDetail : results) {
//			System.out.println(userDetail.getFirstName());
//		}
		System.out.println();
		return results;
		
	}
	
}
