package com.skilldistillery.coderdojo.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class UserDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="dob")
	private Date dob;
	@Column(name="nickname")
	private String nickname;
	@Column(name="phonenumber")
	private String phonenumber;
//	private User user;
	
	@ManyToOne
	@JoinColumn(name="location_id")
	private Location location;
	
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;
	

}
