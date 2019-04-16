package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.UserGoal;

public interface UserGoalRepository extends JpaRepository<UserGoal, Integer> {

}
