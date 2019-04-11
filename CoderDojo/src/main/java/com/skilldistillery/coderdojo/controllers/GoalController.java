package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.List;

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
import com.skilldistillery.coderdojo.services.GoalService;


	@RestController
	@CrossOrigin({ "*", "http://localhost:4202" })
	@RequestMapping("api")
	public class GoalController {
		@Autowired
		private GoalService service;
		
		@GetMapping("goals")
		public List<Goal> findAllGoals(HttpServletRequest req, HttpServletResponse res, Principal principal) {
			return service.findAllGoal();
		}
		
	
		@GetMapping("goals/{gid}")
		public Goal findGoalById(@PathVariable("gid") Integer id, HttpServletResponse response,
				HttpServletRequest request,  Principal principal) {
			try {
				Goal p = service.findGoalById(id);
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
	
		@PostMapping("goals")
		public Goal createGoals(@RequestBody Goal goal, HttpServletResponse response,
				HttpServletRequest request,  Principal principal) {
			try {
				System.out.println("controller.createGoals(): " + goal);
				service.create(goal);
				StringBuffer url = request.getRequestURL();
				System.out.println("goalController" + url.toString());
				url.append("/");
				url.append(goal.getId());
				response.setHeader("Location", url.toString());
				response.setStatus(201);
				return goal;
			} catch (Exception e) {
				response.setStatus(400);
				return null;
			}
	
		}
	
		@DeleteMapping("goals/{gid}")
		public Boolean delete(@PathVariable("gid") Integer id, 
				HttpServletResponse response, HttpServletRequest request,  
				Principal principal) {
			try {
				if (service.findGoalById(id) == null) {
					response.setStatus(404);
					return false;
				} else {
					service.delete(id);
					response.setStatus(204);
					return true;
				}
	
			} catch (Exception e) {
				response.setStatus(409);
				return false;
			}
	
		}
	
		  @PutMapping("goals/{gid}")
		    public Goal putGoal(@PathVariable("gid") Integer id,
		            @RequestBody Goal goal,
		            HttpServletResponse resp,  Principal principal) {
			  goal = service.update(id, goal);
		        if (goal == null) {
		            resp.setStatus(404);
		        }
		        return goal;
		    }
	}

