package com.skilldistillery.coderdojo.services;

import java.util.Set;

import com.skilldistillery.coderdojo.entities.Meeting;

public interface MeetingService {

	Set<Meeting> findAllMeetings();

	Set<Meeting> findAllMeetings(String name);

	Meeting create(String username, Meeting meeting);

	Meeting update(String username, int mid, Meeting meeting);

	boolean destroy(String username, int mid);

}
