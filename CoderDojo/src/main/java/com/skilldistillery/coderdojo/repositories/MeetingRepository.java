package com.skilldistillery.coderdojo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
//
//	@Query(value = "select m from Meeting m \n" + 
//			"Left JOIN meeting_attendance ON meeting.id=meeting_attendance.meeting_id \n" + 
//			"Join user_detail ON meeting_attendance.user_id = user_detail.id\n" + 
//			"where user_detail.location_id=1;")
//	List<UserDetail> findBymeetingAttendees_userDetail(Integer meetingId);
	List<Meeting> findAllByLocationId (int id);
}
