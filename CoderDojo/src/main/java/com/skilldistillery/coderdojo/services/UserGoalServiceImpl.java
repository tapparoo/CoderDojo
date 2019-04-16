package com.skilldistillery.coderdojo.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.UserAchievement;
import com.skilldistillery.coderdojo.entities.UserGoal;
import com.skilldistillery.coderdojo.repositories.UserGoalRepository;

@Service
public class UserGoalServiceImpl implements UserGoalService {

	
	@Autowired
	private UserGoalRepository repo;
	
	@Override
	public List<UserGoal> findAllUserGoal() {
		List<UserGoal> results = repo.findAll();
		return results;
	}

	@Override
	public UserGoal findUserGoalById(Integer id) {
		UserGoal result = null;
		Optional<UserGoal> a = repo.findById(id);
		if (a.isPresent()) {
			result = a.get();
		}
		return result;
	}

	@Override
	public UserGoal create(UserGoal goal) {
			System.out.println(goal);
			repo.saveAndFlush(goal);
			System.out.println(goal);
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
	public UserGoal update(Integer id, UserGoal goal) {
		Optional<UserGoal> opt = repo.findById(id);
		if(opt.isPresent()) {
			
			UserGoal managed = opt.get();
			if(managed.getCompleted()!= goal.getCompleted()) {
				if (managed.getCompleted()) {
					managed.setCompletedDate(null);
				}else if(!managed.getCompleted()) {
					long time = System.currentTimeMillis();
					java.sql.Date date = new java.sql.Date(time);
					managed.setCompletedDate(date);
				}
			}
			managed.setCompleted(goal.getCompleted());
//			managed.setCompletedDate(goal.getCompletedDate());
			managed.setGoal(goal.getGoal());
//			managed.setUserAchievement(goal.getUserAchievement());
			repo.saveAndFlush(managed);
			
		}
		return goal;
	}

}
