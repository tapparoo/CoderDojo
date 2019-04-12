package com.skilldistillery.coderdojo.controllers;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.services.MeetingService;

@RestController
@CrossOrigin({ "*", "http://localhost:4202" })
@RequestMapping("api")
public class MeetingController {
	
	@Autowired
	private MeetingService service;

	//  GET Meetings
	@GetMapping("meetings")
	public Set<Meeting> index(HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		System.out.println(principal+ "werwer");
		return service.findAllMeetings(principal.getName());
	}

//	@GetMapping("todos/{tid}")
//	public Meeting show(HttpServletRequest req, HttpServletResponse res,
//			@PathVariable("tid") Integer tid,
//			Principal principal) {
//		try {
//			Todo todo = service.show(principal.getName(),tid);
//			if (todo == null) {
//				res.setStatus(404);
//			} else {
//				StringBuffer url = req.getRequestURL();
//				url.append("/");
//				url.append(tid);
//				res.setHeader("Location", url.toString());
//
//				res.setStatus(201);
//			}
//
//			return todo;
//		} catch (Exception e) {
//			res.setStatus(500);
//			return null;
//		}
//	}

	@PostMapping("meetings")
	public Meeting create(HttpServletRequest req, HttpServletResponse res, 
			@RequestBody Meeting meeting,
			Principal principal) {
		try {
			service.create(principal.getName(),meeting);
			StringBuffer url = req.getRequestURL();
			System.out.println("PostController" + url.toString());
			url.append("/");
			url.append(meeting.getId());
			res.setHeader("Location", url.toString());
			res.setStatus(201);
			return meeting;
		} catch (Exception e) {
			res.setStatus(400);
			e.printStackTrace();
			return null;
		}
	}
//
//	@PutMapping("todos/{tid}") 
//	public Todo update(
//			HttpServletRequest req, 
//			HttpServletResponse res,
//			@PathVariable("tid") Integer tid,
//			@RequestBody Todo todo,
//			Principal principal) {
//		todo = service.update(principal.getName(),tid, todo);
//	        if (todo == null) {
//	            res.setStatus(404);
//	        }
//	        return todo;
//		
//	}
//
//	@DeleteMapping("todos/{tid}")
//	public Boolean destroy(
//			HttpServletRequest req,
//			HttpServletResponse res, 
//			@PathVariable("tid") Integer tid,
//			Principal principal) {
//		try {
//			if (service.show(principal.getName(), tid) == null) {
//				res.setStatus(404);
//				return false;
//			} else {
//				service.destroy(principal.getName(), tid);
//				res.setStatus(204);
//				return true;
//			}
//
//		} catch (Exception e) {
//			res.setStatus(409);
//			return false;
//		}
//		
//	}

}
