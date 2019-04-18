package com.skilldistillery.coderdojo.services;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.entities.MeetingAttendee;

public interface MeetingAttendeeService {
	MeetingAttendee update(String username, Meeting meeting, MeetingAttendee ma);
	MeetingAttendee showMAById(Integer mid, Long uid);
	MeetingAttendee register(Integer mid, Long uid);
}
