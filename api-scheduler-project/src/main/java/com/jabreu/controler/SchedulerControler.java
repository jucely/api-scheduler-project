package com.jabreu.controler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jabreu.entity.Event;
import com.jabreu.entity.Track;
import com.jabreu.service.EventService;
import com.jabreu.service.SchedulerService;
import com.jabreu.service.TrackService;

@Component
public class SchedulerControler {

	@Autowired
	private EventService eventService;

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private TrackService trackService;

	public Event includeEvent(String eventNameInput) {
		return eventService.includeEvent(eventNameInput);
	}

	public Track includeTrack(String trackName, Date date) {
		return trackService.includeTrack(trackName, date);
	}

	public Boolean scheduleEventIncludedInAvailableSessions() {
		return schedulerService.scheduleEventIncludedInAvailableSessions();
	}
	
	public List<Track> findAllTrackIncluded(){
		return trackService.findAllTrackIncluded();
	}
	
}
