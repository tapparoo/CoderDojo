package com.skilldistillery.coderdojo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Goal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="completed")
	private boolean completed;
	
	@ManyToOne
	@JoinColumn(name = "achievement_id")
	private Achievement achievement;
	
	
	
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
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Achievement getAchievement() {
		return achievement;
	}
	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}
	@Override
	public String toString() {
		return "Goal [id=" + id + ", name=" + name + ", description=" + description + ", completed=" + completed
				+ ", achievement=" + achievement + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((achievement == null) ? 0 : achievement.hashCode());
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Goal other = (Goal) obj;
		if (achievement == null) {
			if (other.achievement != null)
				return false;
		} else if (!achievement.equals(other.achievement))
			return false;
		if (completed != other.completed)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Goal(int id, String name, String description, boolean completed, Achievement achievement) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.completed = completed;
		this.achievement = achievement;
	}
	public Goal() {
		super();
	}
	
}
