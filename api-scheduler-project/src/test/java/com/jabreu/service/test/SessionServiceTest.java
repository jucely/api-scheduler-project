package com.jabreu.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

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
import com.jabreu.entity.Event;
import com.jabreu.entity.Session;
import com.jabreu.service.EventService;
import com.jabreu.service.SessionService;
import com.jabreu.utils.DateUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SessionServiceTest {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private EventService eventService;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private SessionDAO sessionDAO;

	@Test
	public void testSessionInclude() {
		Session session = sessionService.includeSession(new Date(), 300, 12);

		assertNotNull(session.getId(), "The id is must be defined.");
		assertNotNull(session.getLengths(), "The Lengths is must be defined.");
		assertNotNull(session.getLengthsLeft(), "The LengthsLeft is must be defined.");
		assertNotNull(session.getTimeStart(), "The TimeStart is must be defined.");
		assertNotNull(session.getTimeEnd(), "The TimeEnd is must be defined.");

		assertEquals(session.getLengths(), session.getLengthsLeft(), "The Lengths and LengthsLeft are equals.");
		assertEquals(session.getTimeStart(), session.getTimeEnd(), "The TimeStart and TimeEnd are equals.");

		assertEquals(300, session.getLengths().intValue(), "The Lengths expected is 300.");

	}

	@Test
	public void testScheduleEventInSession() {
		Event event = eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");
		Session session = sessionService.includeSession(new Date(), 300, 12);

		assertTrue(sessionService.scheduleEventInSession(event, session));

		session = sessionService.findById(session.getId());

		assertEquals(1, session.getSchedule().size(), "One event schedule expected.");
		assertEquals("12:00PM", session.getSchedule().get(0).getTimeStart(), "The event schedule stats 12:00PM.");
		assertEquals(240, session.getLengthsLeft().intValue(), "The LengthsLeft is 240 now.");
		assertNotEquals(session.getTimeStart(), session.getTimeEnd(), "The TimeStart and TimeEnd are not equals.");
		assertEquals("01:00PM", DateUtil.getHourFormat(session.getTimeEnd()), "The TimeEnd is 01:00PM.");
	}

	@Test
	public void testSchedule2EventInSession() {
		Event event1 = eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");
		Event event2 = eventService.includeEvent("Overdoing it in Python 45min");

		Session session = sessionService.includeSession(new Date(), 300, 12);

		assertTrue(sessionService.scheduleEventInSession(event1, session), "Event1 was scheduled.");
		assertTrue(sessionService.scheduleEventInSession(event2, session), "Event1 was scheduled.");

		session = sessionService.findById(session.getId());
		assertEquals(2, session.getSchedule().size(), "Two event schedule expected.");

		assertEquals(195, session.getLengthsLeft().intValue(), "The LengthsLeft is 195 now.");
		assertEquals("01:00PM", session.getSchedule().get(1).getTimeStart(), "The event schedule stats 01:00PM.");
		assertEquals("01:45PM", DateUtil.getHourFormat(session.getTimeEnd()), "The TimeEnd is 01:45PM.");

	}

	@Test
	public void testScheduleEventListInSession() {
		Event event1 = eventService.includeEvent("Writing Fast Tests Against Enterprise Rails 60min");
		Event event2 = eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");
		Event event3 = eventService.includeEvent("Programming in the Boondocks of Seattle 30min");
		Event event4 = eventService.includeEvent("Ruby vs. Clojure for Back-End Development 30min");
		Event event5 = eventService.includeEvent("Clojure Ate Scala (on my project)assertTrue 15min");
		Event event6 = eventService.includeEvent("A World Without HackerNews ");

		List<Event> events = eventService.findAllEventsOrderByLengthsDesc();

		Session session = sessionService.includeSession(new Date(), 200, 12);

		sessionService.scheduleEventListInSession(events, session);

		session = sessionService.findById(session.getId());

		assertEquals(6, session.getSchedule().size(), "The number of events scheduled must be 6.");

		assertEquals(event1.getName(), session.getSchedule().get(0).getName(), "The first event must be event1.");
		assertEquals("12:00PM", session.getSchedule().get(0).getTimeStart(), "The first event would start at 12:00PM.");

		assertEquals(event2.getName(), session.getSchedule().get(1).getName(),
				"TheassertTrue second event must be event2.");
		assertEquals("01:00PM", session.getSchedule().get(1).getTimeStart(),
				"The second event would start at 01:00PM.");

		assertEquals(event3.getName(), session.getSchedule().get(2).getName(), "The thrid event must be event3.");
		assertEquals("02:00PM", session.getSchedule().get(2).getTimeStart(), "The thrid event would start at 02:00PM.");

		assertEquals(event4.getName(), session.getSchedule().get(3).getName(), "The fourth event must be event4.");
		assertEquals("02:30PM", session.getSchedule().get(3).getTimeStart(),
				"The fourth event would start at 02:30PM.");

		assertEquals(event5.getName(), session.getSchedule().get(4).getName(), "The fifth event must be event5.");
		assertEquals("03:00PM", session.getSchedule().get(4).getTimeStart(), "The fifth event would start at 03:00PM.");

		assertEquals(event6.getName(), session.getSchedule().get(5).getName(), "The sixth event must be event6.");
		assertEquals("03:15PM", session.getSchedule().get(5).getTimeStart(), "The sixth event would start at 03:15PM.");

		assertEquals(0, session.getLengthsLeft().intValue(), "The time left should be 0.");
	}

	@Test
	public void testScheduleEventDoesntFitInSession() {
		Event event = eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");
		Session session = sessionService.includeSession(new Date(), 30, 12);

		assertFalse(sessionService.scheduleEventInSession(event, session));
	}

	@Test
	public void testIncludeSessionWithParamHourLessThanZero() {
		assertThrows(IllegalArgumentException.class, () -> sessionService.includeSession(new Date(), 300, -1));
	}

	@Test
	public void testIncludeSessionWithParamHourMoreThan23() {
		assertThrows(IllegalArgumentException.class, () -> sessionService.includeSession(new Date(), 300, 24));
	}

	@Test
	public void testIncludeSessionWithParamLengthsLessThanZero() {
		assertThrows(IllegalArgumentException.class, () -> sessionService.includeSession(new Date(), -1, 12));
	}

	@Test
	public void testIncludeSessionWithNullParamDateStart() {
		assertThrows(NullPointerException.class, () -> sessionService.includeSession(null, 300, 12));
	}

	@Test
	public void testIncludeSessionWithNullParamLengths() {
		assertThrows(NullPointerException.class, () -> sessionService.includeSession(new Date(), null, 13));
	}

	@Test
	public void testIncludeSessionWithNullParamHourStart() {
		assertThrows(NullPointerException.class, () -> sessionService.includeSession(new Date(), 200, null));
	}

	@AfterEach
	public void deleteAll() {
		eventDAO.deleteAll();
		sessionDAO.deleteAll();
	}

}
