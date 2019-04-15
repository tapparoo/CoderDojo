package com.skilldistillery.coderdojo.services;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.entities.MeetingAttendee;

public interface MeetingAttendeeService {
	MeetingAttendee showMAById(String username, Integer mid);
	
	MeetingAttendee update(String username, Meeting meeting, MeetingAttendee ma);

}
