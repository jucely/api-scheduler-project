package com.jabreu.controler.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jabreu.Application;
import com.jabreu.controler.SchedulerControler;
import com.jabreu.dao.EventDAO;
import com.jabreu.dao.TrackDAO;
import com.jabreu.entity.Track;
import com.jabreu.service.EventService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SchedulerControleTest {

	@Autowired
	private EventService eventService;

	@Autowired
	private TrackDAO trackDAO;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private SchedulerControler schedulerControler;

	@BeforeEach
	public void intIncludeBase() throws ParseException {
		schedulerControler.includeEvent("Writing Fast Tests Against Enterprise Rails 60min");
		schedulerControler.includeEvent("Overdoing it in Python 45min");
		schedulerControler.includeEvent("Lua for the Masses 30min");
		schedulerControler.includeEvent("Ruby Errors from Mismatched Gem Versions 45min");
		schedulerControler.includeEvent("Common Ruby Errors 45min");
		schedulerControler.includeEvent("Rails for Python Developers lightning");
		schedulerControler.includeEvent("Communicating Over Distance 60min");
		schedulerControler.includeEvent("Accounting-Driven Development 45min");
		schedulerControler.includeEvent("Woah 30min");
		schedulerControler.includeEvent("Sit Down and Write 30min");
		schedulerControler.includeEvent("Pair Programming vs Noise 45min");
		schedulerControler.includeEvent("Rails Magic 60min");
		schedulerControler.includeEvent("Ruby on Rails: Why We Should Move On 60min");
		schedulerControler.includeEvent("Clojure Ate Scala (on my project) 45min");
		schedulerControler.includeEvent("Programming in the Boondocks of Seattle 30min");
		schedulerControler.includeEvent("Ruby vs. Clojure for Back-End Development 30min");
		schedulerControler.includeEvent("Ruby on Rails Legacy App Maintenance 60min");
		schedulerControler.includeEvent("A World Without HackerNews 30min");
		schedulerControler.includeEvent("User Interface CSS in Rails Apps 30min");

		schedulerControler.includeTrack("Track1", DateUtils.parseDate("01/06/2019", "dd/MM/yyyy"));
		schedulerControler.includeTrack("Track2", DateUtils.parseDate("02/06/2019", "dd/MM/yyyy"));
	}

	@Test
	public void testValidationSheduleFunction() {
		assertEquals(19, eventService.findAllEventsOrderByLengthsDesc().size(), "Expected 19 events included.");
		assertEquals(2, schedulerControler.findAllTrackIncluded().size(), "Expected 2 tracks included.");

		assertTrue(schedulerControler.scheduleEventIncludedInAvailableSessions(), "All events were schedule.");

		long numberEventSchedule = 0l;

		for (Track track : schedulerControler.findAllTrackIncluded()) {

			assertTrue(track.getMorningSession().getLengthsLeft() >= 0,
					"Not include more event that fits in the morning session.");
			assertNotEquals(0, track.getMorningSession().getSchedule().size(),
					"At least 1 event was include in the morning session.");

			numberEventSchedule += track.getMorningSession().getSchedule().size();

			assertTrue(track.getAfternoonSession().getLengthsLeft() >= 0,
					"Not include more event that fits in the afternoon session.");

			assertNotEquals(0, track.getAfternoonSession().getSchedule().size(),
					"At least 1 event was include in the afternoon session.");

			numberEventSchedule += track.getAfternoonSession().getSchedule().size();

		}

		assertEquals(19, numberEventSchedule, "Expected 19 events included.");

	}

	@AfterEach
	public void deleteAll() {
		trackDAO.deleteAll();
		eventDAO.deleteAll();
	}

}
