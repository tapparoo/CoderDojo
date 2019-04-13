package com.skilldistillery.coderdojo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skilldistillery.coderdojo.entities.Meeting;
import com.skilldistillery.coderdojo.entities.UserDetail;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
//
//	@Query(value = "select m from Meeting m \n" + 
//			"Left JOIN meeting_attendance ON meeting.id=meeting_attendance.meeting_id \n" + 
//			"Join user_detail ON meeting_attendance.user_id = user_detail.id\n" + 
//			"where user_detail.location_id=1;")
//	List<UserDetail> findBymeetingAttendees_userDetail(Integer meetingId);

}
