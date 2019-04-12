package com.skilldistillery.coderdojo.services;

import java.util.List;

import com.skilldistillery.coderdojo.entities.Achievement;

public interface AchievementService {

	List<Achievement> findAllAchievement();

	Achievement findAchievementById(Integer id);

	Achievement create(Achievement achievement);

	boolean delete(Integer id);

	Achievement update(Integer id, Achievement achievement);
}
