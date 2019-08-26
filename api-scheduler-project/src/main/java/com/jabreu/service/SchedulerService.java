package com.jabreu.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jabreu.entity.Event;
import com.jabreu.entity.Session;
import com.jabreu.exception.NoTimeLeftException;

@Component
public class SchedulerService {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private EventService eventService;

	public Boolean scheduleEventIncludedInAvailableSessions() {
		List<Event> events = eventService.findAllEventsOrderByLengthsDesc();
		return this.scheduleEventListInAvailableSessions(events);
	}

	protected Boolean scheduleEventListInAvailableSessions(List<Event> events) {
		Iterator<Event> itEvent = events.iterator();

		boolean eventWasSchedule = true;
		Event event = null;

		while (hasMoreEventToSchedule(itEvent)) {
			event = eventWasSchedule ? getTheNextEvent(itEvent) : event;

			for (Session session : sessionService.allSessionsOrderByLengthsLeftDesc()) {
				eventWasSchedule = sessionService.scheduleEventInSession(event, session);

				if (!eventWasSchedule && !sessionService.verifyIfHasSessionWithLengthsLeft(event.getLengths())) {
					throw new NoTimeLeftException();
				}

				event = eventWasSchedule && hasMoreEventToSchedule(itEvent) ? getTheNextEvent(itEvent) : event;
				eventWasSchedule = false;
				continue;
			}
		}
		return true;
	}

	protected Event getTheNextEvent(Iterator<Event> itEvent) {
		return itEvent.next();
	}

	protected boolean hasMoreEventToSchedule(Iterator<Event> itEvent) {
		return itEvent.hasNext();
	}

}
