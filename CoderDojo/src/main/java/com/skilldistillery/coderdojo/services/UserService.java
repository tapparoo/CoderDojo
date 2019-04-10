package com.skilldistillery.coderdojo.services;

import com.skilldistillery.coderdojo.entities.User;

public interface UserService {

	User getUserByUsername(String username);

}
