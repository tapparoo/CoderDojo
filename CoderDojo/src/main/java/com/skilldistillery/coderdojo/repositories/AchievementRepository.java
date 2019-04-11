package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

}
