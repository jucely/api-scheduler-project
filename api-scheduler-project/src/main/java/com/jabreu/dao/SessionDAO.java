package com.jabreu.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jabreu.entity.Session;

public interface SessionDAO extends CrudRepository<Session, Long> {

	@Query(value = "SELECT s FROM Session s")
	List<Session> findAll(Sort sort);
	
	List<Session> findByLengthsLeftGreaterThanEqual(Integer lengthsLeft);
	
}
