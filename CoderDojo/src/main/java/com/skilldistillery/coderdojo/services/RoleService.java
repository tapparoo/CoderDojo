package com.skilldistillery.coderdojo.services;

import java.util.List;

import com.skilldistillery.coderdojo.entities.Role;
import com.skilldistillery.coderdojo.entities.UserDetail;

public interface RoleService {

	Role findByName(String name);
	List<UserDetail> findUsersByRole(String role);
	List<Role> index();

}
