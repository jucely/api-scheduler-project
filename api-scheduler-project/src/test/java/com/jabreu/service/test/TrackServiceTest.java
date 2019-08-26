package com.jabreu.service.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jabreu.Application;
import com.jabreu.dao.TrackDAO;
import com.jabreu.entity.Track;
import com.jabreu.service.TrackService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TrackServiceTest {
	
	@Autowired
	private TrackService trackService;
	
	@Autowired
	private TrackDAO trackDAO;

	@Test
	public void testIncludeTrack() {
		Track track = trackService.includeTrack("Track1", new Date());

		assertNotNull(track.getId(), "The id is must be defined.");
		assertNotNull(track.getName(), "The name is must be defined.");
		assertNotNull(track.getAfternoonSession(), "The AfternoonSession is must be defined.");
		assertNotNull(track.getMorningSession(), "The MorningSession is must be defined.");
		assertNotNull(track.getLunchEvent(), "The LunchEvent is must be defined.");
		assertNotNull(track.getNetworkingEvent(), "The NetworkingEvent is must be defined.");
	}

	@Test
	public void testIncludeTrackWithNullParamName() {
		assertThrows(NullPointerException.class, () -> trackService.includeTrack(null, new Date()));
	}

	@Test
	public void testIncludeTrackWithNullParamDate() {
		assertThrows(NullPointerException.class, () -> trackService.includeTrack("Track23", null));
	}

	@Test
	public void testIncludeTrackWithEmptyParamName() {
		assertThrows(IllegalArgumentException.class, () -> trackService.includeTrack("", new Date()));
	}

	@AfterEach
	public void deleteAll() {
		trackDAO.deleteAll();
	}

}
