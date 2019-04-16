package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
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
	public List<UserAchievement> findAllUserAchievement(HttpServletRequest req, HttpServletResponse res, Principal principal) {
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
	
	@PostMapping("userachievements")
	public UserAchievement createAchievements(@RequestBody UserAchievement achievement, HttpServletResponse response,
			HttpServletRequest request, Principal principal) {
		try {
			System.out.println("controller.createAchievements(): " + achievement);
			service.create(achievement);
			StringBuffer url = request.getRequestURL();
			System.out.println("achievementController" + url.toString());
			url.append("/");
			// url.append(achievement.getId());
			response.setHeader("Location", url.toString());
			response.setStatus(201);
			return achievement;
		} catch (Exception e) {
			response.setStatus(400);
			return null;
		}

	}

	@DeleteMapping("userachievements/{aid}")
	public Boolean delete(@PathVariable("aid") Integer id, HttpServletResponse response, HttpServletRequest request,
			Principal principal) {
		try {
			if (service.findUserAchievementById(id) == null) {
				response.setStatus(404);
				return false;
			} else {
				if (service.findUserAchievementById(id).getUserGoals().size() > 0) {
					System.out.println("made it into the goal delete if statement.");
					UserAchievement ach = service.findUserAchievementById(id);
					Set<UserGoal> goals = ach.getUserGoals();
					for (UserGoal goal : goals) {
						userGoalService.delete(goal.getId());
					}
				}
				service.delete(id);
				response.setStatus(204);
				return true;
			}

		} catch (Exception e) {
			response.setStatus(409);
			return false;
		}

	}

	@PutMapping("userachievements/{aid}")
	public UserAchievement putUserAchievement(@PathVariable("aid") Integer id, @RequestBody UserAchievement achievement,
			HttpServletResponse resp, Principal principal) {
		achievement = service.update(id, achievement);
		if (achievement == null) {
			resp.setStatus(404);
		}
		return achievement;
	}
	
	@GetMapping("userachievements/user/{username}")
	public List<UserAchievement> findAllUserAchievementByUser(@PathVariable("username") String username, HttpServletRequest req, HttpServletResponse res, Principal principal) {
		UserDetail ud = deets.findUserDetailByUsername(username);
		List<UserAchievement>  results = service.findAllUserAchievementByUserId(ud);
		return results;
	}
}
	


