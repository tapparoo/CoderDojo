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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="user_detail")
public class UserDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="dob")
	private Date dob;
	@Column(name="nickname")
	private String nickname;
	@Column(name="phone_number")
	private String phoneNumber;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="location_id")
	private Location location;
	
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;
	
	@JsonIgnore
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="parent_child_relationship",
		joinColumns={@JoinColumn(name="child_id")},
		inverseJoinColumns={@JoinColumn(name="parent_id")})
	private Set<UserDetail> parents = new HashSet<UserDetail>();

	@JsonIgnore
	@ManyToMany(mappedBy="parents")
	private Set<UserDetail> children = new HashSet<UserDetail>();
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "meeting_attendance", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "meeting_id"))
	private Set<Meeting> meetingsAttended;
	
	public Set<Meeting> getMeetingsAttended() {
		return meetingsAttended;
	}

	public void setMeetingsAttended(Set<Meeting> meetingsAttended) {
		this.meetingsAttended = meetingsAttended;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<UserDetail> getParents() {
		return parents;
	}

	public void setParents(Set<UserDetail> parents) {
		this.parents = parents;
	}

	public Set<UserDetail> getChildren() {
		return children;
	}

	public void setChildren(Set<UserDetail> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		UserDetail other = (UserDetail) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDetail [id=" + id + ", dob=" + dob + ", nickname=" + nickname + ", phoneNumber=" + phoneNumber
				+ ", user=" + user + ", location=" + location + ", address=" + address;
	}
}
