package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {


}
