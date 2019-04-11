package com.skilldistillery.coderdojo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Transactional
@Repository
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public User register(User user) {
		String encodedPW = encoder.encode(user.getPassword());
		user.setPassword(encodedPW); // only persist encoded password
		// set other fields to default values
		repo.saveAndFlush(user);
		return user;
	}
}
