package com.skilldistillery.coderdojo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.entities.MeetingAttendee;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.repositories.MeetingAttendeeRepository;
import com.skilldistillery.coderdojo.repositories.MeetingRepository;
import com.skilldistillery.coderdojo.repositories.UserDetailRepository;
import com.skilldistillery.coderdojo.repositories.UserRepository;
@Service
public class MeetingAttendeeServiceImpl implements MeetingAttendeeService {

	@Autowired
	private MeetingAttendeeRepository repo;
	
	@Autowired
	private MeetingRepository repoMeeting;
	@Autowired
	private UserDetailRepository repoUserDetail;
    @Autowired
    private UserRepository repoUser;
    
	@Override
	public MeetingAttendee showMAById(Integer mid, Long uid) {
    	Optional<Meeting> meeting = repoMeeting.findById(mid);
    	Optional<UserDetail> user = repoUserDetail.findById(uid);
    	
    	MeetingAttendee m = null;
    	
        if (meeting.isPresent() && user.isPresent()) {
            m = repo.findByMeetingIdAndUserDetailId(mid, uid);
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
	                repo.saveAndFlush(managed);
	                return managed;
	            }
	            }
	        }
	        return null;
	}
	
	@Override
	public MeetingAttendee register(Integer mid, Long uid) {
		Optional<UserDetail> userOpt = repoUserDetail.findById(uid);
		Optional<Meeting> meetingOpt = repoMeeting.findById(mid);
		MeetingAttendee ma = null;
		
		if (userOpt.isPresent() && meetingOpt.isPresent()){
			UserDetail user = userOpt.get();
			Meeting meeting = meetingOpt.get();
			
			ma = new MeetingAttendee();
			ma.setAttended(false);
			ma.setMeeting(meeting);
			ma.setUserDetail(user);
			repo.saveAndFlush(ma);
			ma = showMAById(mid, uid);
		}
		return ma;
	}

}
