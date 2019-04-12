package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
	UserDetail findByUserId(long id);
}
