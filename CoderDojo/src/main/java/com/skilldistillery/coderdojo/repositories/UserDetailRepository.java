package com.skilldistillery.coderdojo.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.coderdojo.entities.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
	UserDetail findByUserId(long id);
	
	@Query("Select ud from UserDetail ud JOIN FETCH ud.user u JOIN FETCH u.roles r WHERE r.name = :role")
	Set<UserDetail> findUsersByRole(@Param("role")String role);
	
}
