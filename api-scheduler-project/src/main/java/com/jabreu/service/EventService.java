package com.jabreu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.jabreu.dao.EventDAO;
import com.jabreu.entity.Event;
import com.jabreu.utils.EventDurationParse;
import com.jabreu.validator.EventInputValidator;

@Component
public class EventService {

	@Autowired
	private EventDAO eventDAO;

	public Event includeEvent(String eventInput) {
		validateIncludeEventParams(eventInput);

		Event event = new Event(eventInput, EventDurationParse.toInteger(eventInput));

		return eventDAO.save(event);

	}

	protected void validateIncludeEventParams(String eventInput) {
		if (!EventInputValidator.thisEventIsValid(eventInput)) {
			throw new IllegalArgumentException("This eventInput is not valid: " + eventInput);
		}
	}

	public List<Event> findAllEventsOrderByLengthsDesc() {
		return eventDAO.findAll(new Sort(Sort.Direction.DESC, "lengths"));
	}

}
