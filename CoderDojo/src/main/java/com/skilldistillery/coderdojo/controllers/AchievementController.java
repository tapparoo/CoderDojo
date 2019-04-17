package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
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

import com.skilldistillery.coderdojo.entities.Achievement;
import com.skilldistillery.coderdojo.entities.Goal;
import com.skilldistillery.coderdojo.services.AchievementService;
import com.skilldistillery.coderdojo.services.GoalService;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
@RequestMapping("api")
public class AchievementController {
	@Autowired
	private AchievementService service;
	@Autowired
	private GoalService goalService;

	@GetMapping("achievements")
	public List<Achievement> findAllAchievements(HttpServletRequest req, HttpServletResponse res, Principal principal) {
		return service.findAllAchievement();
	}

	@GetMapping("achievements/{aid}")
	public Achievement findAchievementById(@PathVariable("aid") Integer id, HttpServletResponse response,
			HttpServletRequest request, Principal principal) {
		try {
			Achievement p = service.findAchievementById(id);
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
	
	@PostMapping("achievements")
	public Achievement createAchievements(@RequestBody Achievement achievement, HttpServletResponse response,
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

	@DeleteMapping("achievements/{aid}")
	public Boolean delete(@PathVariable("aid") Integer id, HttpServletResponse response, HttpServletRequest request,
			Principal principal) {
		try {
			if (service.findAchievementById(id) == null) {
				response.setStatus(404);
				return false;
			} else {
//				if (service.findAchievementById(id).getGoals().size() > 0) {
//					System.out.println("made it into the goal delete if statement.");
//					Achievement ach = service.findAchievementById(id);
//					Set<Goal> goals = ach.getGoals();
//					for (Goal goal : goals) {
//						goalService.delete(goal.getId());
//					}
//				}
				service.delete(id);
				response.setStatus(204);
				return true;
			}

		} catch (Exception e) {
			response.setStatus(409);
			return false;
		}

	}

	@PutMapping("achievements/{aid}")
	public Achievement putAchievement(@PathVariable("aid") Integer id, @RequestBody Achievement achievement,
			HttpServletResponse resp, Principal principal) {
		achievement = service.update(id, achievement);
		if (achievement == null) {
			resp.setStatus(404);
		}
		return achievement;
	}
}
