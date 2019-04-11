package com.skilldistillery.coderdojo.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="parent_child_relationship",
		joinColumns={@JoinColumn(name="id")},
		inverseJoinColumns={@JoinColumn(name="child_id")})
	private Set<UserDetail> parents = new HashSet<UserDetail>();

	@ManyToMany(mappedBy="parents")
	private Set<UserDetail> children = new HashSet<UserDetail>();


	

}
