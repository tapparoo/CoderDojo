package com.skilldistillery.coderdojo.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

	

@Entity	
@Table(name="user_achievement")

public class UserAchievement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean achieved;

	@Column(name = "achieved_date")
	private Date achievedDate;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_detail_id")
	private UserDetail userDetail;

	@ManyToOne
	@JoinColumn(name = "achievement_id")
	private Achievement achievement;

	@OneToMany(mappedBy = "userAchievement", cascade=CascadeType.ALL)
	private Set<UserGoal> userGoals;

	public Achievement getAchievement() {
		return achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}

	public Set<UserGoal> getUserGoals() {
		return userGoals;
	}

	public void setUserGoals(Set<UserGoal> userGoals) {
		this.userGoals = userGoals;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getAchieved() {

		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}

	public Date getAchievedDate() {
		return achievedDate;
	}

	public void setAchievedDate(Date achievedDate) {
		this.achievedDate = achievedDate;
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
		UserAchievement other = (UserAchievement) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserAchievement [id=" + id + ", achieved=" + achieved + ", achievedDate=" + achievedDate
				+ ", userDetail=" + userDetail + ", achievement=" + achievement + "]";
	}

	public UserAchievement(int id, boolean achieved, Date achievedDate, UserDetail userDetail,
			Achievement achievement) {
		super();
		this.id = id;
		this.achieved = achieved;
		this.achievedDate = achievedDate;
		this.userDetail = userDetail;
		this.achievement = achievement;
	}

	public UserAchievement() {
		super();
	}

}
