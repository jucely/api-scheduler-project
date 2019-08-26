package com.jabreu.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.time.DateUtils;

import com.jabreu.utils.DateUtil;

@Entity
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Scheduling> schedule;

	@Column(nullable = false)
	private Integer lengths;

	@Column(nullable = false)
	private Integer lengthsLeft;

	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	private Date timeStart;

	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	private Date timeEnd;

	public Session() {}
	
	public Session(Integer lengths, Date timeStart) {
		super();
		this.lengths = lengths;
		this.lengthsLeft = lengths;
		this.timeStart = timeStart;
		this.timeEnd = timeStart;
		this.schedule = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public List<Scheduling> getSchedule() {
		return Collections.unmodifiableList(schedule);
	}

	public Integer getLengths() {
		return lengths;
	}

	public Integer getLengthsLeft() {
		return lengthsLeft;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}
	
	public boolean scheduleEvent(Event event) {
		if(theEventFitsInSession(event)) {
			Scheduling scheduling = new Scheduling(event.getName(), DateUtil.getHourFormat(timeEnd), this);
			lengthsLeft -= event.getLengths();
			timeEnd = DateUtils.addMinutes(timeEnd, event.getLengths());
			schedule.clear();
			schedule.add(scheduling);
			return true;
		}
		return false;
	}
	
	private boolean theEventFitsInSession(Event event) {
		return lengthsLeft >= event.getLengths();
	}

}
