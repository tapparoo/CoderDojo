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

class UserDetailTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private UserDetail userDeets;
	private UserDetail mom;
	

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
		userDeets = em.find(UserDetail.class, 2);
		mom = em.find(UserDetail.class, 1);
		System.out.println(userDeets.getParents());
	}

	@AfterEach
	void tearDown() throws Exception {
		userDeets = null;
		em.close();
	}

	@Test
	void test_user_detail_mapping() {
		assertEquals(2, userDeets.getId());
		assertEquals("Greenwood Village", userDeets.getAddress().getCity());
		assertTrue(userDeets.getParents().contains(mom));
	
	
	}


}
