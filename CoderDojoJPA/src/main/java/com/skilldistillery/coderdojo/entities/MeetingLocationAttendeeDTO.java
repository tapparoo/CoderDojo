package com.skilldistillery.coderdojo.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class MeetingLocationAttendeeDTO {

	private String name;
	
	private Date scheduledTime;

	private Location location;
	
	private List<MeetingAttendee> meetingAttendees;
	
	private String nameLocation;

	private String details;
	
	private Address address;
	
	private boolean attended;

	public MeetingLocationAttendeeDTO(String name, Date scheduledTime, Location location,
			List<MeetingAttendee> meetingAttendees, String nameLocation, String details, Address address,
			boolean attended) {
		super();
		this.name = name;
		this.scheduledTime = scheduledTime;
		this.location = location;
		this.meetingAttendees = meetingAttendees;
		this.nameLocation = nameLocation;
		this.details = details;
		this.address = address;
		this.attended = attended;
	}

}
