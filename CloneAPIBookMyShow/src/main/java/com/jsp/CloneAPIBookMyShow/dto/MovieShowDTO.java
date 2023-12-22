package com.jsp.CloneAPIBookMyShow.dto;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.jsp.CloneAPIBookMyShow.enums.ShowStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieShowDTO {

	private long showId;
	
	@NotNull(message = "Showstrat time not be blank..")
	@DateTimeFormat(style = "HH:mm")
	private LocalTime showStartTime;
	
	@NotNull(message = "ShowEnd time not be blank..")
	@DateTimeFormat(style = "HH:mm")
	private LocalTime showEndTime;
	
	//SHOW STATUS ENUM
	@NotNull(message = "Show Status can't be null..")
	private ShowStatus showStatus;
	private String showLocation;
	
	@NotNull(message = "Movie Id can't be empty...")
	private long movieId;
	private String movieName;
	
	//GENRE     
	private String genre;
	
	@DateTimeFormat(style = "HH:mm")
	private LocalTime movieDuration;
	private String movieDescription;
	private String movieLanguage;
	
	@NotNull(message = "Movie Id can't be empty")
	private long screenId;
	private String screenName;
	
	@NotNull(message = "Classic_Seat Price invalid..")
	private double classicSeatPrice;
	
	@NotNull(message = "Gold_Seat Price invalid..")
	private double goldSeatPrice;
	
	@NotNull(message = "Platinum_Seat Price invalid..")
	private double platinumSeatPrice;
	
}