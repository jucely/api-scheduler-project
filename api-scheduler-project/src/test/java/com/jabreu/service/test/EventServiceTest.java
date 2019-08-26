package com.jabreu.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.jabreu.entity.Event;
import com.jabreu.service.EventService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class EventServiceTest {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventDAO eventDAO;

	@Test
	public void testEventInclude() {
		Event event = eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");

		assertNotNull(event.getId(), "The id is must be defined.");
		assertNotNull(event.getLengths(), "The lenghts is must be defined.");
		assertNotNull(event.getName(), "The name is must be defined.");
		assertTrue(60 == event.getLengths(), "Expected 60minutes");
		
		List<Event> events = eventService.findAllEventsOrderByLengthsDesc();

		assertEquals(1, events.size(), "Expected 1 event included");

	}

	@Test
	public void testEventSortDuration() {
		eventService.includeEvent("Writing Fast Tests Against Enterprise Rails");
		eventService.includeEvent("Overdoing it in Python 45min");
		eventService.includeEvent("Common Ruby Errors 30min");
		eventService.includeEvent("Ruby on Rails: Why We Should Move On 60min");

		List<Event> events = eventService.findAllEventsOrderByLengthsDesc();

		assertEquals(4, events.size(), "Expected 4 events included");

		assertEquals(60, events.get(0).getLengths().intValue(), "Expected 60minutes");
		assertEquals(45, events.get(1).getLengths().intValue(), "Expected 60minutes");
		assertEquals(30, events.get(2).getLengths().intValue(), "Expected 60minutes");
		assertEquals(5, events.get(3).getLengths().intValue(), "Expected 60minutes");
	}

	@Test
	public void testIncludeEventWithInvalidInput() {
		assertThrows(IllegalArgumentException.class,
				() -> eventService.includeEvent("Writing Fast Tests Against Enterprise Rails 6a"),
				"Expected InvalidEventInputException when include invalid event");

	}

	@Test
	public void testIncludeEventWithNullInput() {
		assertThrows(IllegalArgumentException.class, () -> eventService.includeEvent(null),
				"Expected InvalidEventInputException when include null event");

	}

	@Test
	public void testIncludeEventWithEmptyInput() {
		assertThrows(IllegalArgumentException.class, () -> eventService.includeEvent(""),
				"Expected InvalidEventInputException when include empty event");
	}

	@AfterEach
	public void deleteAll() {
		eventDAO.deleteAll();
	}

}
