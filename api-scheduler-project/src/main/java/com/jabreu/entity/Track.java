package com.jabreu.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Track {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idMorningSession", unique = true, nullable = false)
	private Session morningSession;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idAfternoonSession", unique = true, nullable = false)
	private Session afternoonSession;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idLunchEvent", unique = true, nullable = false)
	private Scheduling lunchEvent;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idNetworkingEvent", unique = true, nullable = false)
	private Scheduling networkingEvent;

	public Track() {
	}

	public Track(String name, Date date, Session morningSession, Session afternoonSession, Scheduling lunchEvent,
			Scheduling networkingEvent) {
		super();
		this.name = name;
		this.date = date;
		this.morningSession = morningSession;
		this.afternoonSession = afternoonSession;
		this.lunchEvent = lunchEvent;
		this.networkingEvent = networkingEvent;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Session getMorningSession() {
		return morningSession;
	}

	public Session getAfternoonSession() {
		return afternoonSession;
	}

	public Scheduling getLunchEvent() {
		return lunchEvent;
	}

	public Scheduling getNetworkingEvent() {
		return networkingEvent;
	}

	public Date getDate() {
		return date;
	}

}
