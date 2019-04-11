package com.skilldistillery.coderdojo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.RoleRepository;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // New Users: 
    //    default role 'STUDENT'
    //	  default enabled = true
    //	  encode password before saving to DB
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(roleRepository.findByName("STUDENT"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public List<User> index(){
    	return userRepository.findAll();
    }
}
