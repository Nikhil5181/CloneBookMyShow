package com.jsp.CloneAPIBookMyShow.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsp.CloneAPIBookMyShow.enums.ShowStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MovieShow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long showId;
	@DateTimeFormat(style = "HH:mm")
	private LocalTime showStartTime;
	@DateTimeFormat(style = "HH:mm")
	private LocalTime showEndTime;
	
	//showStatus Enum
	private ShowStatus showStatus;
	private String showLocation;
	
	//duplicate
	private long movieId;
	private String movieName;
	
	//genre     
	private String genre;
	@DateTimeFormat(style = "HH:mm")
	private LocalTime movieDuration;
	private String movieDescription;
	private String movieLanguage;
	
	
	private long screenId;
	private String screenName;
	private double classicSeatPrice;
	private double goldSeatPrice;
	private double platinumSeatPrice;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Theatre theatre;
}