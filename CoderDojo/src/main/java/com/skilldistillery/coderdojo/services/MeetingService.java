package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Set;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.entities.UserDetail;

public interface MeetingService {

	Set<Meeting> findAllMeetings();

	Set<Meeting> findAllMeetings(String name);

	Meeting create(String username, Meeting meeting);

	Meeting update(String username, int mid, Meeting meeting);

	boolean destroy(String username, int mid);
	
	Meeting show(String username, Integer mid);
	
	List<Meeting> findAllByLocationId(String username, Integer mid);

//	List<UserDetail> showMeetingUsers(Integer mid);

}
