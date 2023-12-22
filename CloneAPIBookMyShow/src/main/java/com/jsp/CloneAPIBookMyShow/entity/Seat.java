package com.jsp.CloneAPIBookMyShow.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jsp.CloneAPIBookMyShow.enums.SeatStatus;
import com.jsp.CloneAPIBookMyShow.enums.SeatType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seatId;
//	seat type
	private SeatType seatType;
	private SeatStatus seatStatus;
	
	@ManyToOne
	@JoinColumn
	private Screen screen;
}
