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

class RoleTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Role role;
	

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
		role = em.find(Role.class, (long)1);
	}

	@AfterEach
	void tearDown() throws Exception {
		role = null;
		em.close();
	}

	@Test
	void test_role_mapping() {
		assertEquals("ADMIN", role.getName());
		assertTrue(role.getUsers().size()>0);
		
	
	}

}
