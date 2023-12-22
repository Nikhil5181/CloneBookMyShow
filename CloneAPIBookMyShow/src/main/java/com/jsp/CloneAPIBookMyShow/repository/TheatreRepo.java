package com.jsp.CloneAPIBookMyShow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CloneAPIBookMyShow.entity.Theatre;

public interface TheatreRepo extends JpaRepository<Theatre,Long> {

	public List<Theatre> findByTheaterName(String theaterName);
	
}
