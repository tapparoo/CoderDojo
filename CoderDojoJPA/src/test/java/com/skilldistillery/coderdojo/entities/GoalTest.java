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

class GoalTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Goal goal;

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
		goal = em.find(Goal.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		goal = null;
		em.close();
	}

	@Test
	void test_achievement_mapping() {
		assertEquals(1, goal.getId());
		assertEquals("turn computer on....", goal.getName());
		assertEquals("Turning the computer on is serious bisiness", goal.getDescription());
		assertEquals("White Belt", goal.getAchievement().getName());

	}
}
