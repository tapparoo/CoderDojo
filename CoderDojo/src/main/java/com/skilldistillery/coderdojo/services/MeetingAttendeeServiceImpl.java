package com.skilldistillery.coderdojo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.entities.MeetingAttendee;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.MeetingAttendeeRepository;
import com.skilldistillery.coderdojo.repositories.MeetingRepository;
import com.skilldistillery.coderdojo.repositories.UserRepository;
@Service
public class MeetingAttendeeServiceImpl implements MeetingAttendeeService {

	@Autowired
	private MeetingAttendeeRepository repo;
	
	@Autowired
	private MeetingRepository repoMeeting;
	
	
    @Autowired
    private UserRepository repoUser;
    
	@Override
	public MeetingAttendee showMAById(String username, Integer mid) {
    	Optional<MeetingAttendee> opt = repo.findById(mid);
    	User u = repoUser.findByUsername(username);
    	MeetingAttendee m = null;
        if (opt.isPresent()) {
            if (u!= null) {
                m = opt.get();
                System.out.println(m + "&&&&&&&&&&&&&&&&&&&&&&");
            }
        }
        return m;
	}

	@Override
	public MeetingAttendee update(String username, Meeting meeting, MeetingAttendee ma) {
	       Optional<MeetingAttendee> opt = repo.findById(ma.getId());
	       MeetingAttendee managed = opt.get();
	        if (opt.isPresent()) {
	        	
	        	Optional<Meeting> meeOpt = repoMeeting.findById(meeting.getId());
	        	User u = repoUser.findByUsername(username);
	        	 if (meeOpt.isPresent()) {
	            if (u!= null) {
	                managed.setAttended(ma.isAttended());
//	                managed.setMeeting(ma.getMeeting());
	                repo.saveAndFlush(managed);
	                return managed;
	            }
	            }
	        }
	        return null;
	}

}
