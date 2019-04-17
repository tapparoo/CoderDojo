package com.skilldistillery.coderdojo.services;

import java.util.List;

import com.skilldistillery.coderdojo.entities.UserGoal;

public interface UserGoalService {
	List<UserGoal> findAllUserGoal();
	

	UserGoal findUserGoalById(Integer id);

	UserGoal create(UserGoal goal);

	boolean delete(Integer id);

	UserGoal update(Integer id, UserGoal goal);
	

}
