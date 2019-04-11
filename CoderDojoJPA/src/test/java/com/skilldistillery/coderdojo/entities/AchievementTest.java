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

class AchievementTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Achievement ahievement;

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
		ahievement = em.find(Achievement.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		ahievement = null;
		em.close();
	}

	@Test
	void test_achievement_mapping() {
		assertEquals("You are just starting. this is exciting!", ahievement.getDescription());
		assertEquals(1, ahievement.getId());
		assertEquals("https://i.imgur.com/eouDBcn.gif", ahievement.getImageUrl());
		assertEquals("White Belt", ahievement.getName());

	}


}
