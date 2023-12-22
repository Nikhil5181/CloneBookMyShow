package com.jsp.CloneAPIBookMyShow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CloneAPIBookMyShow.entity.Screen;
import com.jsp.CloneAPIBookMyShow.entity.Seat;
import com.jsp.CloneAPIBookMyShow.enums.SeatStatus;

public interface SeatRepo extends JpaRepository<Seat,Long> {

	long countSeatBySeatStatusAndScreen(SeatStatus seatStatus ,Screen screen);
	long countSeatByScreen(Screen screen);
	
}