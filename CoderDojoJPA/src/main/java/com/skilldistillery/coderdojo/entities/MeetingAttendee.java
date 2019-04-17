package com.skilldistillery.coderdojo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="meeting_attendance")
public class MeetingAttendee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private boolean attended = false;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="meeting_id")
	private Meeting meeting;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetail userDetail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeetingAttendee other = (MeetingAttendee) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MeetingAttendee [id=" + id + ", attended=" + attended + "]";
	}

	public MeetingAttendee(int id, boolean attended, Meeting meeting, UserDetail userDetail) {
		super();
		this.id = id;
		this.attended = attended;
		this.meeting = meeting;
		this.userDetail = userDetail;
	}

	public MeetingAttendee() {
		super();
	}
}
