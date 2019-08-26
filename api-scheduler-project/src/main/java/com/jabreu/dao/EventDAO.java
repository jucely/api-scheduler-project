package com.jabreu.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jabreu.entity.Event;

public interface EventDAO extends CrudRepository<Event, Long> {

	@Query(value = "SELECT e FROM Event e")
	List<Event> findAll(Sort sort);
	
}
