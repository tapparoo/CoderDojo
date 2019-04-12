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

class MeetingAttendeeTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private MeetingAttendee meetingAttendee;

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
		meetingAttendee = em.find(MeetingAttendee.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		meetingAttendee = null;
		em.close();
	}

	@Test
	void test_meeting_attendee_mapping() {
		assertEquals(1, meetingAttendee.getId());
		assertEquals("Meetup at Turing", meetingAttendee.getMeeting().getName());
		assertEquals("A.J.", meetingAttendee.getUserDetail().getNickname());
		
	
	}

}
