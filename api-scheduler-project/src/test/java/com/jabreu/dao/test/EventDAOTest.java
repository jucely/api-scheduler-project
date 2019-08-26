package com.jabreu.dao.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jabreu.dao.EventDAO;
import com.jabreu.entity.Event;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EventDAOTest {

	@Autowired
	private EventDAO eventDAO;

	@Test
	public void testSaveEvent() {
		Event event1 = new Event("Writing Fast Tests Against Enterprise Rails", 60);

		eventDAO.save(event1);

		List<Event> events = (List<Event>) eventDAO.findAll();

		assertEquals(1, eventDAO.count(), "Expected 1 iten in the list.");
		assertNotNull(events.get(0).getId(), "The id must be defined.");
	}

	@Test
	public void testDeleteNullParameter() {
		assertThrows(InvalidDataAccessApiUsageException.class, () -> eventDAO.delete(null));
	}

	@AfterEach
	public void deleteAll() {
		eventDAO.deleteAll();
	}
}
