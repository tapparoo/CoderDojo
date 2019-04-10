package com.skilldistillery.coderdojo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.UserRepository;
import com.skilldistillery.coderdojo.security.SecurityConfig;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private SecurityConfig config;
	
	@Override
	public User register(User user) {
//		String encodedPW = encoder.encode(user.getPassword());
//		user.setPassword(encodedPW); // only persist encoded password
//		user.setEnabled(true);
//		user.setRole("standard");
		
//		repo.saveAndFlush(user);
		return new User();
	}
}
