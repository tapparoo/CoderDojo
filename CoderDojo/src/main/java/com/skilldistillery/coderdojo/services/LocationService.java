package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Set;

import com.skilldistillery.coderdojo.entities.Location;
import com.skilldistillery.coderdojo.entities.UserDetail;

public interface LocationService {
	Set<Location> findAllLocation();
	List<UserDetail> findUsersByLocation(int lid);
}
