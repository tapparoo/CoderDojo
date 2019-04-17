package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.Goal;
import com.skilldistillery.coderdojo.entities.UserAchievement;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.entities.UserGoal;
import com.skilldistillery.coderdojo.repositories.UserDetailRepository;
import com.skilldistillery.coderdojo.services.UserAchievementService;
import com.skilldistillery.coderdojo.services.UserDetailsServiceImpl;
import com.skilldistillery.coderdojo.services.UserGoalService;
import com.skilldistillery.coderdojo.services.UserService;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
@RequestMapping("api")
public class UserAchievementController {

	@Autowired
	private UserAchievementService service;

	@Autowired
	private UserGoalService userGoalService;

	@Autowired
	private UserDetailsServiceImpl deets;

	@GetMapping("userachievements")
	public List<UserAchievement> findAllUserAchievement(HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		return service.findAllUserAchievement();
	}

	@GetMapping("userachievements/{aid}")
	public UserAchievement findAchievementById(@PathVariable("aid") Integer id, HttpServletResponse response,
			HttpServletRequest request, Principal principal) {
		try {
			UserAchievement p = service.findUserAchievementById(id);
			if (p == null) {
				response.setStatus(404);
			} else {
				StringBuffer url = request.getRequestURL();
				url.append("/");
				url.append(id);
				response.setHeader("Location", url.toString());

				response.setStatus(201);
			}

			return p;
		} catch (Exception e) {
			response.setStatus(500);
			return null;
		}
	}

	@PostMapping("userachievements/{uid}")
	public UserAchievement createAchievements(@PathVariable("uid") String userId,
			@RequestBody UserAchievement userAchievement, HttpServletResponse response, HttpServletRequest request,
			Principal principal) {
		try {
			System.out.println("controller.createAchievements(): " + userAchievement);
			userAchievement.setUserDetail(deets.findUserDetailByUsername(userId));
			service.create(userAchievement);
			System.out.println(userAchievement);
			Set<Goal> achGoals = userAchievement.getAchievement().getGoals();
			Set<UserGoal> userGoals = new HashSet<UserGoal>();
//			
			for (Goal goal : achGoals) {
				System.out.println(goal.getName());

				UserGoal userGoal = new UserGoal();
				userGoal.setCompleted(false);
				userGoal.setCompletedDate(null);
				userGoal.setGoal(goal);
				userGoal.setUserAchievement(userAchievement);
				System.out.println(userGoal);
				userGoalService.create(userGoal);
			}
			userAchievement.setUserGoals(userGoals);
			service.update(userAchievement.getId(), userAchievement, userAchievement.getUserDetail());
//			

			StringBuffer url = request.getRequestURL();
			System.out.println("achievementController" + url.toString());
			url.append("/");
			// url.append(achievement.getId());
			response.setHeader("Location", url.toString());
			response.setStatus(201);
			return userAchievement;
		} catch (Exception e) {
			response.setStatus(400);
			return null;
		}

	}

	@DeleteMapping("userachievements/{aid}")
	public Boolean delete(@PathVariable("aid") Integer id, HttpServletResponse response, HttpServletRequest request,
			Principal principal) {
		System.out.println("delete(): id:" + id);
		try {
			if (service.findUserAchievementById(id) == null) {
				System.out.println("delete(): id:" + id + " not found.");
				response.setStatus(404);
				return false;
			} else {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				service.delete(id);
				response.setStatus(204);
				return true;
			}

		} catch (Exception e) {
			System.out.println("delete(): id:" + id + " service threw exception.");
			response.setStatus(409);
			return false;
		}

	}

	@PutMapping("users/{uid}/userachievements/{aid}")
	public UserAchievement putUserAchievement(@PathVariable("uid") String uid, @PathVariable("aid") Integer id,
			@RequestBody UserAchievement achievement, HttpServletResponse resp, Principal principal) {
		UserDetail user = deets.findUserDetailByUsername(uid);
		achievement = service.update(id, achievement, user);
		if (achievement == null) {
			resp.setStatus(404);
		}
		return achievement;
	}

	@GetMapping("userachievements/user/{username}")
	public List<UserAchievement> findAllUserAchievementByUser(@PathVariable("username") String username,
			HttpServletRequest req, HttpServletResponse res, Principal principal) {
		UserDetail ud = deets.findUserDetailByUsername(username);
		List<UserAchievement> results = service.findAllUserAchievementByUserId(ud);
		return results;
	}
}
