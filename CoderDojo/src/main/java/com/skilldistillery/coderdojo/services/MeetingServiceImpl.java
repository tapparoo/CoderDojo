package com.skilldistillery.coderdojo.services;

import java.util.HashSet;
import java.util.Optional;
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

	@Override
	public Meeting create(String username, Meeting meeting) {
		User u = repoUser.findByUsername(username);
		  if (u != null) {
			  return repo.saveAndFlush(meeting);
		}

		return null;
	}

	@Override
	public Meeting update(String username, int mid, Meeting meeting) {
	       Optional<Meeting> opt = repo.findById(mid);
	        if (opt.isPresent()) {
	        	Meeting managed = opt.get();
	        	User u = repoUser.findByUsername(username);
	            if (u!= null) {
	                managed.setName(meeting.getName());
	                managed.setScheduledTime(meeting.getScheduledTime());
	                repo.saveAndFlush(managed);
	                return managed;
	            }
	        }
	        return null;
	}

	@Override
	public boolean destroy(String username, int mid) {
        Optional<Meeting> opt = repo.findById(mid);
        if (opt.isPresent()) {
        	User u = repoUser.findByUsername(username);
            if (u!= null) {
                repo.deleteById(mid);
                return true;
            }
        }
        return false;
	}

}
