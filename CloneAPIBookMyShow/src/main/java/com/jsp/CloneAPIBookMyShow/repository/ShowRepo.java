package com.jsp.CloneAPIBookMyShow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CloneAPIBookMyShow.entity.MovieShow;

public interface ShowRepo extends JpaRepository<MovieShow,Long> {

}
