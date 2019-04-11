package com.skilldistillery.coderdojo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MeetingAttendee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="attended")
	private boolean attended;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetail userDetail;
	
	@ManyToOne
	@JoinColumn(name="meeting_id")
	private Meeting meeting;
//	TODO: im not sure if this should be an entity or part of something else. 
	
}
