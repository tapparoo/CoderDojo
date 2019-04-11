package com.skilldistillery.coderdojo.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserGoalTest {


	private static EntityManagerFactory emf;
	private EntityManager em;
	private UserGoal userGoal;

	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("CodeDojoJPA");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		userGoal = em.find(UserGoal.class, 2);
	}

	@AfterEach
	void tearDown() throws Exception {
		userGoal = null;
		em.close();
	}

	@Test
	void test_achievement_mapping() {
		assertEquals(2, userGoal.getId());
		assertEquals("2019-04-10", userGoal.getCompletedDate().toString());
		assertEquals("install all dev tools", userGoal.getGoal().getName());
		assertEquals(2, userGoal.getUserAchievement().getId());
	
	
	}

}
