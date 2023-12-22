package com.jsp.CloneAPIBookMyShow.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.jsp.CloneAPIBookMyShow.enums.BookingStatus;
import com.jsp.CloneAPIBookMyShow.enums.SeatType;

import lombok.Data;

@Entity
@Data
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bookingId;
	/*Valid time this to that */
	@DateTimeFormat(style = "HH:mm")
	private LocalTime bookingFromTime;
	@DateTimeFormat(style = "HH:mm")
	private LocalTime bookingTillTime;
	private long seatId;
	//seat type Enum
	private SeatType seatType;
	
	//Enum 
	private BookingStatus bookingStatus;
	private double seatPrice;
}
