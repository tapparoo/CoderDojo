package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Achievement;
import com.skilldistillery.coderdojo.entities.UserAchievement;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.entities.UserGoal;
import com.skilldistillery.coderdojo.repositories.UserAchievementRepository;

@Service
public class UserAchievementServiceImpl implements UserAchievementService {

	@Autowired
	private UserAchievementRepository repo;

	@Override
	public List<UserAchievement> findAllUserAchievement() {
		List<UserAchievement> results = repo.findAll();
		return results;
	}

	@Override
	public UserAchievement findUserAchievementById(Integer id) {
		UserAchievement result = null;
		Optional<UserAchievement> a = repo.findById(id);
		if (a.isPresent()) {
			result = a.get();
		}
		return result;
	}

	@Override
	public UserAchievement create(UserAchievement achievement) {
		repo.saveAndFlush(achievement);
		return achievement;
	}

	@Override
	public boolean delete(Integer id) {
		boolean deleted = false;
		if (repo.existsById(id)) {
			repo.deleteById(id);
			deleted = true;
		}
		return deleted;
	}

	@Override
	public UserAchievement update(Integer id, UserAchievement achievement, UserDetail user) {
		Optional<UserAchievement> opt = repo.findById(id);
		if (opt.isPresent()) {
			UserAchievement managed = opt.get();
			achievement.setUserDetail(user);
			if (user.getUser().getUsername() == managed.getUserDetail().getUser().getUsername()) {
				
				if (managed.getAchieved() != achievement.getAchieved()) {
					if (managed.getAchieved()) {
						managed.setAchievedDate(null);
					} else if (!managed.getAchieved()) {
						long time = System.currentTimeMillis();
						java.sql.Date date = new java.sql.Date(time);
						managed.setAchievedDate(date);
					}
				}
				managed.setAchieved(achievement.getAchieved());
//			managed.setUserDetail(achievement.getUserDetail());
//			managed.setAchievedDate(achievement.getAchievedDate());
				managed.setAchievement(achievement.getAchievement());
				managed.setUserDetail(achievement.getUserDetail());
				managed.setUserGoals(achievement.getUserGoals());
				System.out.println(managed.getUserDetail().getUser().getUsername());
				repo.saveAndFlush(managed);

				return achievement;
			}
		}
		return null;
	}

	@Override
	public List<UserAchievement> findAllUserAchievementByUserId(UserDetail userDetail) {
		List<UserAchievement> results = repo.findByUserDetail(userDetail);
		return results;
	}

}
