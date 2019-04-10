package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByUsername(String username);
}
