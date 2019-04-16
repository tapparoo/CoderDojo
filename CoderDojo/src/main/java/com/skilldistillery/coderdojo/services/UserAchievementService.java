package com.skilldistillery.coderdojo.services;

import java.util.List;

import com.skilldistillery.coderdojo.entities.UserAchievement;
import com.skilldistillery.coderdojo.entities.UserDetail;

public interface UserAchievementService {
	
	List<UserAchievement> findAllUserAchievement();
	
	UserAchievement findUserAchievementById(Integer id);

	UserAchievement create(UserAchievement achievement);

	boolean delete(Integer id);

	UserAchievement update(Integer id, UserAchievement achievement);
	
	List<UserAchievement> findAllUserAchievementByUserId(UserDetail userDetail);
	
}


