package com.jabreu.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jabreu.Application;
import com.jabreu.dao.EventDAO;
import com.jabreu.dao.SessionDAO;
import com.jabreu.entity.Session;
import com.jabreu.exception.NoTimeLeftException;
import com.jabreu.service.EventService;
import com.jabreu.service.SchedulerService;
import com.jabreu.service.SessionService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SchedulerServiceTest {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private EventService eventService;

	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private SessionDAO sessionDAO;

	@Test
	public void testSchedulerAllEventsAvailableInSession() {
		Session session1 = sessionService.includeSession(new Date(), 300, 12);
		Session session2 = sessionService.includeSession(new Date(), 300, 12);

		eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");
		eventService.includeEvent("Overdoing it in Python 45min");

		assertTrue(schedulerService.scheduleEventIncludedInAvailableSessions(), "All events Available were schedule.");
		session1 = sessionService.findById(session1.getId());
		session2 = sessionService.findById(session2.getId());
		
		assertEquals(1, session1.getSchedule().size(), "One event schedule expected in session1.");
		assertEquals(1, session2.getSchedule().size(), "One event schedule expected in session2.");

	}

	@Test
	public void testThereIsntSessionThatFitsAllEventsAvailable() {
		sessionService.includeSession(new Date(), 60, 12);
		sessionService.includeSession(new Date(), 30, 12);

		eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");
		eventService.includeEvent("Overdoing it in Python 45min");

		assertThrows(NoTimeLeftException.class, () -> schedulerService.scheduleEventIncludedInAvailableSessions(),
				"Expected NoTimeLeftException when try schedule event without time");

	}

	@AfterEach
	public void deleteAll() {
		sessionDAO.deleteAll();
		eventDAO.deleteAll();
	}

}
