package com.skilldistillery.coderdojo.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

// To transfer the user_id and username from UserDetail's 'User' object (but not the password)

public class UserDetailDTO {
	private long id;
	private Date dob;
	private String nickname;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String userImageUrl;
	private long userId;
	private String username;
	
	private Location location;
	private Address address;
	private Set<UserDetail> parents = new HashSet<UserDetail>();
	private Set<UserDetail> children = new HashSet<UserDetail>();
	private Set<Meeting> meetingsAttended;
	public UserDetailDTO(long id, Date dob, String nickname, String phoneNumber, String firstName, String lastName,
			String email, String gender, String userImageUrl, long userId, String username, Location location,
			Address address, Set<UserDetail> parents, Set<UserDetail> children, Set<Meeting> meetingsAttended) {
		super();
		this.id = id;
		this.dob = dob;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.userImageUrl = userImageUrl;
		this.userId = userId;
		this.username = username;
		this.location = location;
		this.address = address;
		this.parents = parents;
		this.children = children;
		this.meetingsAttended = meetingsAttended;
	}
}
