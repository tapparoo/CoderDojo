package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Achievement;
import com.skilldistillery.coderdojo.repositories.AchievementRepository;

@Service
public class AchievementServiceImpl implements AchievementService {

	@Autowired
	private AchievementRepository repo;

	@Override
	public List<Achievement> findAllAchievement() {
		List<Achievement> results = repo.findAll();
		return results;
	}

	@Override
	public Achievement findAchievementById(Integer id) {
		Achievement result = null;
		Optional<Achievement> a = repo.findById(id);
		if (a.isPresent()) {
			result = a.get();
		}
		return result;
	}

	@Override
	public Achievement create(Achievement achievement) {
		repo.saveAndFlush(achievement);
		return achievement;

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
	public Achievement update(Integer id, Achievement achievement) {
		Optional<Achievement> opt = repo.findById(id);
		if(opt.isPresent()) {
			Achievement managed = opt.get();
			managed.setDescription(achievement.getDescription());
			managed.setGoals(achievement.getGoals());
			managed.setImageUrl(achievement.getImageUrl());
			managed.setName(achievement.getName());
			repo.saveAndFlush(managed);
		}
		return achievement;
	}

}
