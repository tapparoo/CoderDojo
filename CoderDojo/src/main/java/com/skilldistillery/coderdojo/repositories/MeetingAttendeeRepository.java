package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.MeetingAttendee;

public interface MeetingAttendeeRepository extends JpaRepository<MeetingAttendee, Integer> {
	MeetingAttendee findByMeetingIdAndUserDetailId(int mid, long uid);
	boolean deleteByMeetingId(int mid);
}
