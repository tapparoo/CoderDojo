package com.skilldistillery.coderdojo.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "user_detail")
public class UserDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "dob")
	private Date dob;
	@Column(name = "nickname")
	private String nickname;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String email;
	private String gender;
	@Column(name = "user_image_url")
	private String userImageUrl;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "user_achievement",
			joinColumns = {@JoinColumn(name = "user_detail_id")},
			inverseJoinColumns = {@JoinColumn(name = "id")})
	private List<Achievement> achievements;
	
	@JsonProperty(access = Access.READ_ONLY)
	@ManyToMany
	@JoinTable(name = "parent_child_relationship", 
		joinColumns = {@JoinColumn(name = "parent_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "child_id") })
	private Set<UserDetail> children;

	@JsonIgnore
	@ManyToMany(mappedBy = "children")
	private Set<UserDetail> parents;

	@JsonIgnore
	@OneToMany
	@JoinTable(name = "meeting_attendance", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "meeting_id"))
	private Set<Meeting> meetingsAttended;
	
	@JsonIgnore
	public boolean isParentOf(UserDetail child) {
		for (UserDetail kid: children) {
			if (child == kid) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addChild(UserDetail child) {
		if (child == null)
			return;
		if (children == null)
			children = new HashSet<>();
		
		children.add(child);
	}
	
	public void removeChild(UserDetail child) {
		if (child == null)
			return;
		
		children.remove(child);
	}
	
	public void addAchievement(Achievement achievement) {
		if (achievement == null)
			return;
		if (achievements == null)
			achievements = new ArrayList<>();
		
		achievements.add(achievement);
		achievement.getUsers().add(this);
	}
	
	public void removeAchievement(Achievement achievement) {
		if (achievement == null)
			return;
		
		achievements.remove(achievement);
		achievement.getUsers().remove(this);
	}
	
	public Set<Meeting> getMeetings() {
		return meetingsAttended;
	}

	public void setMeetingsAttended(Set<Meeting> meetingsAttended) {
		this.meetingsAttended = meetingsAttended;
	}
	
	
	public void addMeeting(Meeting m) {
		if (m == null)
			return;
		if (meetingsAttended == null)
			meetingsAttended = new HashSet<>();
		
		meetingsAttended.add(m);
	}
	
	public void removeMeetingAttended(Meeting m) {
		if (m == null)
			return;
		
		meetingsAttended.remove(m);
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

	public Set<UserDetail> getChildren() {
		return children;
	}

	public void setChildren(Set<UserDetail> children) {
		this.children = children;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
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
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender
				+ ", userImageUrl=" + userImageUrl + ", user=" + user + ", location=" + location + ", address="
				+ address + ", meetingsAttended=" + meetingsAttended + "]";
	}
}
