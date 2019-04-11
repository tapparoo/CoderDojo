package com.skilldistillery.coderdojo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetingTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	Meeting meet;


	@BeforeEach
	public void setUp() throws Exception {
		emf = Persistence.createEntityManagerFactory("CodeDojoJPA");
		em = emf.createEntityManager();
		meet = em.find(Meeting.class, 1);
	}

	@AfterEach
	public void tearDown() throws Exception {
		em.close();
		emf.close();
	}
	
	@Test
	public void test_meeting_table() {
		assertEquals(1, meet.getId());
		assertEquals("Meetup at Turing", meet.getName());
		assertEquals(1, meet.getLocation().getId());
		assertEquals("2019-01-01", meet.getScheduledTime().toString());
	}

}
