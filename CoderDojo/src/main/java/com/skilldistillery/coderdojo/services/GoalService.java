package com.skilldistillery.coderdojo.services;

import java.util.List;

import com.skilldistillery.coderdojo.entities.Goal;

public interface GoalService {

	List<Goal> findAllGoal();

	Goal findGoalById(Integer id);

	void create(Goal goal);

	void delete(Integer id);

	Goal update(Integer id, Goal goal);

}
