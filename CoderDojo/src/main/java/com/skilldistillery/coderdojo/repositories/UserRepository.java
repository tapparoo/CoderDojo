package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.skilldistillery.coderdojo.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User getUserByUsername(String username);
}
