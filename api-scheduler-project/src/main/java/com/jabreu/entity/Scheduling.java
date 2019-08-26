package com.jabreu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Scheduling {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String timeStart;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Session session;

	public Scheduling() {
	};

	public Scheduling(String name, String timeStart, Session session) {
		super();
		this.name = name;
		this.timeStart = timeStart;
		this.session = session;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public Session getSession() {
		return session;
	}

	@Override
	public String toString() {
		return timeStart + " " + name;
	}

}
