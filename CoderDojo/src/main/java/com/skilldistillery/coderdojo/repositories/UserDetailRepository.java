package com.skilldistillery.coderdojo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.coderdojo.entities.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
	UserDetail findByUserId(long id);
	
	@Query("Select ud from UserDetail ud JOIN FETCH ud.user u JOIN FETCH u.roles r WHERE r.name = :role")
//	@Query("Select ud from UserDetail ud join User u on u.id = ud.user_id join userRole ur on ur.user_id = u.id join Role r on r.id = ur.role_id where r.name = :role")
	List<UserDetail> findUsersByRole(@Param("role")String role);
	
}
