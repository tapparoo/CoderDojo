package com.skilldistillery.coderdojo.services;

import java.util.Set;

import com.skilldistillery.coderdojo.entities.Meeting;

public interface MeetingService {

	Set<Meeting> findAllMeetings();

	Set<Meeting> findAllMeetings(String name);

}
