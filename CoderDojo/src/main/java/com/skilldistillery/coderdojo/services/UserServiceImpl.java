package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.repositories.RoleRepository;
import com.skilldistillery.coderdojo.repositories.UserDetailRepository;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailRepository deetsRepo;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// New Users:
	// default role 'STUDENT'
	// default enabled = true
	// encode password before saving to DB
	@Override
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.addRole(roleRepository.findByName("STUDENT"));
		user.setEnabled(true);
		userRepository.save(user);
		
		// Associate new UserDetail object with newly created User
		UserDetail deets = new UserDetail();
		deets.setUser(user);
		deetsRepo.saveAndFlush(deets);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> index() {
		return userRepository.findAll();
	}

	@Override
	public User updateUser(User user) {
		// by id to handle username updates
		Optional<User> optUser = userRepository.findById(user.getId());
		User modifiedUser = null;

		if (optUser.isPresent()) {
			modifiedUser = optUser.get();
			modifiedUser.setUsername(user.getUsername());
			modifiedUser.setPassword(passwordEncoder.encode(user.getPassword()));
			modifiedUser.setEnabled(user.getEnabled());
			userRepository.save(modifiedUser);
		}
		return modifiedUser;
	}

	// Not really using this since the 'enabled' column can be updated directly on the admin page.
	// Might use it if the user wants to 'delete' their own account
	@Override
	public boolean disableUser(String username) {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			return false;
		} else {
			user.setEnabled(false);
			return user.getEnabled() ? false : true;
		}
	}
}
