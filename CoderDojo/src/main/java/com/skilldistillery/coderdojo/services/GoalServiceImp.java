package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Achievement;
import com.skilldistillery.coderdojo.entities.Goal;
import com.skilldistillery.coderdojo.repositories.GoalRepository;
@Service
public class GoalServiceImp implements GoalService {
	
	@Autowired
	private GoalRepository repo;

	@Override
	public List<Goal> findAllGoal() {
		List<Goal> results = repo.findAll();
		return results;
	}

	@Override
	public Goal findGoalById(Integer id) {
		Goal result = null;
		Optional<Goal> a = repo.findById(id);
		if (a.isPresent()) {
			result = a.get();
		}
		return result;
	}

	@Override
	public Goal create(Goal goal) {
//		System.out.println(goal.getAchievement());
		repo.saveAndFlush(goal);
		return goal;

	}

	@Override
	public boolean delete(Integer id) {
		boolean deleted = false;
		if(repo.existsById(id)) {
			repo.deleteById(id);
			deleted=true;	
		}
		return deleted;
	}

	@Override
	public Goal update(Integer id, Goal goal) {
		Optional<Goal> opt = repo.findById(id);
		if(opt.isPresent()) {
			Goal managed = opt.get();
			managed.setDescription(goal.getDescription());
			managed.setName(goal.getName());
			
			repo.saveAndFlush(managed);
		}
		return goal;
	}


}
