package com.jabreu.dao;

import org.springframework.data.repository.CrudRepository;

import com.jabreu.entity.Track;

public interface TrackDAO extends CrudRepository<Track, Long> {

}
