package com.skilldistillery.coderdojo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserAchievement;
import com.skilldistillery.coderdojo.entities.UserDetail;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Integer>  {
	
	
	List<UserAchievement> findByUserDetail(UserDetail userdetail);

}
