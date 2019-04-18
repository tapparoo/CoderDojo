package com.skilldistillery.coderdojo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.coderdojo.entities.Location;
import com.skilldistillery.coderdojo.entities.UserDetail;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	@Query("Select ud from UserDetail ud JOIN FETCH ud.location loc where loc.id = :id")
	List<UserDetail> findUserDetailsByLocationId(@Param("id")Integer id);
}
