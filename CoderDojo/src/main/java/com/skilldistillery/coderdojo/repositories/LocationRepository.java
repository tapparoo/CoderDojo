package com.skilldistillery.coderdojo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.coderdojo.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
