package com.skilldistillery.coderdojo.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Achievement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	private String name;
	
	private String description;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@OneToMany(mappedBy="achievement", cascade=CascadeType.REMOVE)
	private Set<Goal> goals;
	
	@JsonIgnore
	@ManyToMany(mappedBy="achievements")
	private List<UserDetail> users;
	
	@OneToMany(mappedBy="achievement", cascade=CascadeType.REMOVE)
	@JsonIgnore
	private List<UserAchievement> userAchievements;

	
	public void addUser(UserDetail user) {
		if (user == null)
			return;
		if (users == null)
			users = new ArrayList<>();

		users.add(user);
		user.getAchievements().add(this);
	}

	public void removeUser(UserDetail user) {
		if (user == null)
			return;

		users.remove(user);
		user.getAchievements().remove(this);
	}
	
	public List<UserAchievement> getUserAchievements() {
		return userAchievements;
	}

	public void setUserAchievements(List<UserAchievement> userAchievements) {
		this.userAchievements = userAchievements;
	}

	public List<UserDetail> getUsers() {
		return users;
	}

	public void setUsers(List<UserDetail> users) {
		this.users = users;
	}

	public Set<Goal> getGoals() {
		return goals;
	}

	public void setGoals(Set<Goal> goals) {
		this.goals = goals;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Achievement [id=" + id + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
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
		Achievement other = (Achievement) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Achievement(int id, String name, String description, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public Achievement() {
		super();
	}
	  
  
}
