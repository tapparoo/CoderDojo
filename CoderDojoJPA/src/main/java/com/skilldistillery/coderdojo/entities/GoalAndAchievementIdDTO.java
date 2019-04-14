package com.skilldistillery.coderdojo.entities;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GoalAndAchievementIdDTO {
	
	private int id; 
	private String name;
	private String description;
	private int achievementId;
	
	
	@Override
	public String toString() {
		return "GoalAndAchievementIdDTO [id=" + id + ", name=" + name + ", description=" + description
				+ ", achievementId=" + achievementId + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAchievementId() {
		return achievementId;
	}
	public void setAchievementId(int achievementId) {
		this.achievementId = achievementId;
	}
	
	
}
