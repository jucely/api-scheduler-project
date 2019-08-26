package com.jabreu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jabreu.dao.TrackDAO;
import com.jabreu.entity.Scheduling;
import com.jabreu.entity.Session;
import com.jabreu.entity.Track;
import com.jabreu.utils.ConstantesUtil;

@Component
public class TrackService {

	@Autowired
	private TrackDAO trackDAO;

	@Autowired
	private SessionService sessionService;

	public Track includeTrack(String trackName, Date date) {
		validateIncludeTrackParams(trackName, date);

		Scheduling lunch = new Scheduling(ConstantesUtil.LUNCH_EVENT_NAME, ConstantesUtil.LUNCH_TIME_START, null);

		Scheduling networkingEvent = new Scheduling(ConstantesUtil.NETWORKING_EVENT_NAME,
				ConstantesUtil.NETWORKING_TIME_START, null);

		Session morningSession = sessionService.createSession(date, ConstantesUtil.LENGTHS_MORNING_SESSION,
				ConstantesUtil.HOUR_START_MORNING_SESSION);

		Session afternoonSession = sessionService.createSession(date, ConstantesUtil.LENGTHS_AFTERNOON_SESSION,
				ConstantesUtil.HOUR_START_AFTERNOON_SESSION);
		Track track = new Track(trackName, date, morningSession, afternoonSession, lunch, networkingEvent);

		return trackDAO.save(track);
	}

	protected void validateIncludeTrackParams(String trackName, Date date) {
		if (trackName == null) {
			throw new NullPointerException("TrackName can't be null.");
		}
		if (trackName.isEmpty()) {
			throw new IllegalArgumentException("TrackName can't be empty.");
		}
		if (date == null) {
			throw new NullPointerException("Date can't be null.");
		}
	}

	public List<Track> findAllTrackIncluded() {
		return (List<Track>) trackDAO.findAll();
	}

}
