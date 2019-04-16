package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Address;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.repositories.AddressRepository;
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
	@Autowired
	private AddressRepository addrRepo;

	// New Users:
	// default role 'STUDENT'
	// default enabled = true
	// encode password before saving to DB
	@Override
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.addRole(roleRepository.findByName("PARENT"));
		user.setEnabled(true);
		userRepository.saveAndFlush(user);
		
		// Associate new UserDetail object with newly created User
		UserDetail deets = new UserDetail();
		deets.setUser(user);

		// Every new user gets a new address
		deets.setAddress(addrRepo.saveAndFlush(new Address()));
		deetsRepo.saveAndFlush(deets);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findById(long id) {
		Optional<User> opt = userRepository.findById(id);
		User user = null;
		if (opt.isPresent()) {
			user = opt.get();
		}
		return user;
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
		System.out.println("***********");
		System.out.println(user);
		if (optUser.isPresent()) {
			modifiedUser = optUser.get();
			modifiedUser.setUsername(user.getUsername());
			if (user.getPassword() != null  && !user.getPassword().equals(modifiedUser.getPassword())) {
				modifiedUser.setPassword(passwordEncoder.encode(user.getPassword()));
			}
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
