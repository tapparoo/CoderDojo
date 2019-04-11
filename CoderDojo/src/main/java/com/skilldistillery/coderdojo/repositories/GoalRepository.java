package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.Goal;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

}
