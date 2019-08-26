package com.jabreu.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.jabreu.dao.SessionDAO;
import com.jabreu.entity.Event;
import com.jabreu.entity.Session;
import com.jabreu.utils.ConstantesUtil;
import com.jabreu.utils.DateUtil;

@Component
public class SessionService {

	@Autowired
	private SessionDAO sessionDAO;

	public Session createSession(Date start, Integer lengths, Integer hourStart) {
		validateIncludeSessionParams(start, lengths, hourStart);

		Session session = new Session(lengths, DateUtil.setHourWithoutMinutes(start, hourStart));

		return session;
	}

	public Session includeSession(Date start, Integer lengths, Integer hourStart) {
		return sessionDAO.save(createSession(start, lengths, hourStart));
	}

	public Session findById(Long id) {
		Optional<Session> result = sessionDAO.findById(id);
		return result.get();
	}

	protected void validateIncludeSessionParams(Date start, Integer lengths, Integer hourStart) {
		if (start == null) {
			throw new NullPointerException("The param 'start' can't be null in the session.");
		}

		if (lengths == null) {
			throw new NullPointerException("The param 'lengths' can't be null in the session.");
		}

		if (hourStart == null) {
			throw new NullPointerException("The param 'hourStart' can't be null in the session.");
		}

		if (lengths <= ConstantesUtil.FIRST_DAY_HOUR) {
			throw new IllegalArgumentException("The param 'lengths' needs to be more than '0': " + lengths);
		}

		if (hourStart < ConstantesUtil.FIRST_DAY_HOUR || hourStart > ConstantesUtil.LAST_DAY_HOUR) {
			throw new IllegalArgumentException("The param 'hourStart' needs to be between '0' and '23': " + hourStart);
		}
	}

	public void scheduleEventListInSession(List<Event> events, Session session) {
		for (Event event : events) {
			scheduleEventInSession(event, session);
		}
	}

	public boolean scheduleEventInSession(Event event, Session session) {
		if (session.scheduleEvent(event)) {
			sessionDAO.save(session);
			return true;
		}
		return false;
	}



	public List<Session> allSessionsOrderByLengthsLeftDesc() {
		return sessionDAO.findAll(new Sort(Sort.Direction.DESC, "lengthsLeft"));
	}

	public Boolean verifyIfHasSessionWithLengthsLeft(Integer lengthsLeft) {
		List<Session> sessions = sessionDAO.findByLengthsLeftGreaterThanEqual(lengthsLeft);
		return sessions != null && !sessions.isEmpty();
	}

}
