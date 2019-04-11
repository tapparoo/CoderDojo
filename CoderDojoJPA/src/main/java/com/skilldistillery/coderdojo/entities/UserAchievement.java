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
public class UserAchievement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private boolean achieved;
	
	@Column(name="achieved_date")
	private Date achievedDate;
	
	@ManyToOne
	@JoinColumn(name="user_detail_id")
	private UserDetail userDetail;
	
	 
	@ManyToOne
	@JoinColumn(name = "achievement_id")
	private Achievement achievement;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAchieved() {
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
		result = prime * result + (achieved ? 1231 : 1237);
		result = prime * result + ((achievedDate == null) ? 0 : achievedDate.hashCode());
		result = prime * result + ((achievement == null) ? 0 : achievement.hashCode());
		result = prime * result + id;
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
		UserAchievement other = (UserAchievement) obj;
		if (achieved != other.achieved)
			return false;
		if (achievedDate == null) {
			if (other.achievedDate != null)
				return false;
		} else if (!achievedDate.equals(other.achievedDate))
			return false;
		if (achievement == null) {
			if (other.achievement != null)
				return false;
		} else if (!achievement.equals(other.achievement))
			return false;
		if (id != other.id)
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
