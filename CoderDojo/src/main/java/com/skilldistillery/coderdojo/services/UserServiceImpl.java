package com.skilldistillery.coderdojo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Repository
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repo;

	public List<User> index(){
		return repo.findAll();
	}

}
