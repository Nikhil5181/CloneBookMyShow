package com.jsp.CloneAPIBookMyShow.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsp.CloneAPIBookMyShow.enums.ScreenAvailability;
import com.jsp.CloneAPIBookMyShow.enums.ScreenStatus;
import com.jsp.CloneAPIBookMyShow.enums.ScreenType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Screen {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long screenId; 
	private String screenName;
//	SCREEN TYPE
	private ScreenType screenType;
//	SCREEN AVAILABILITY
	private ScreenAvailability screenAvailable;	
//	SCREEN STATUS
	private ScreenStatus screenStatus;
	
	
	@OneToMany(mappedBy = "screen" , cascade = CascadeType.ALL)
	@JsonIgnore             
	private List<Seat> seats;
	
	private int noOfClassicSeats;
	private int noOfPlatinumSeats;
	private int noOfGoldSeats;
	
	@ManyToOne
	@JoinColumn
	private Theatre theatre;
	
}