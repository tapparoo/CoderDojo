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

class UserAchievementTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private UserAchievement ua;
	

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
		ua = em.find(UserAchievement.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		ua = null;
		em.close();
	}

	@Test
	void test_user_achievement_mapping() {
		assertEquals(1, ua.getId());
		assertEquals("2019-04-07", ua.getAchievedDate().toString());
		assertEquals("White Belt", ua.getAchievement().getName());
		assertEquals("a-tappy", ua.getUserDetail().getNickname());
		assertTrue(ua.getUserGoals().size()>0);
	
	
	}


}
