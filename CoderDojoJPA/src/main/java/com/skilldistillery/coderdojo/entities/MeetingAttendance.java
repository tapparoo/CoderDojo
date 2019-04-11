package com.skilldistillery.coderdojo.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class MeetingAttendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private boolean attended;
	
	@ManyToOne
	@JoinColumn(name="meeting_id")
	private Meeting meeting;
	
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
		result = prime * result + (attended ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((meeting == null) ? 0 : meeting.hashCode());
		result = prime * result + ((userDetail == null) ? 0 : userDetail.hashCode());
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
		MeetingAttendance other = (MeetingAttendance) obj;
		if (attended != other.attended)
			return false;
		if (id != other.id)
			return false;
		if (meeting == null) {
			if (other.meeting != null)
				return false;
		} else if (!meeting.equals(other.meeting))
			return false;
		if (userDetail == null) {
			if (other.userDetail != null)
				return false;
		} else if (!userDetail.equals(other.userDetail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MeetingAttendance [id=" + id + ", attended=" + attended + ", meeting=" + meeting + ", userDetail="
				+ userDetail + "]";
	}

	public MeetingAttendance(int id, boolean attended, Meeting meeting, UserDetail userDetail) {
		super();
		this.id = id;
		this.attended = attended;
		this.meeting = meeting;
		this.userDetail = userDetail;
	}

	public MeetingAttendance() {
		super();
	}
	
	
	
}
