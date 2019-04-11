package com.skilldistillery.coderdojo.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.MeetingRepository;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private MeetingRepository repo;
	
    @Autowired
    private UserRepository repoUser;
    
	@Override
	public Set<Meeting> findAllMeetings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Meeting> findAllMeetings(String username) {
		Set<Meeting> meetings= null;
		User u = repoUser.findByUsername(username);
		System.out.println(u+ "############33");
		if(u != null) {
			System.out.println(u+ "############44");
			meetings = new HashSet<Meeting>(repo.findAll());
			return meetings;
		}

		return null;
	}

}
