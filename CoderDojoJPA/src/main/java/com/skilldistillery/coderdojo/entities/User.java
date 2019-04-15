package com.skilldistillery.coderdojo.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private boolean enabled;

	@JsonIgnore
	@Transient
	private String passwordConfirm;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "user_role", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@JsonIgnore
	public boolean isAdmin() {
		boolean admin = false;
		for (Role role : roles) {
			if (role.getName().equalsIgnoreCase("admin")) {
				admin = true;
				break;
			}
		}
		return admin;
	}
	
	public boolean isStudent() {
		boolean student = false;
		for (Role role : roles) {
			if (role.getName().equalsIgnoreCase("student")) {
				student = true;
				break;
			}
		}
		return student;
	}
	
	public boolean isParent() {
		boolean parent = false;
		for (Role role : roles) {
			if (role.getName().equalsIgnoreCase("parent")) {
				parent = true;
				break;
			}
		}
		return parent;
	}
	
	public void addRole(Role role) {
		if (role == null)
			return;
		if (roles == null)
			roles = new HashSet<>();

		roles.add(role);
		role.getUsers().add(this);
	}

	public void removeRole(Role role) {
		if (role == null)
			return;

		roles.remove(role);
		role.getUsers().remove(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", passwordConfirm=" + passwordConfirm;
	}

}
