package com.jsp.CloneAPIBookMyShow.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.jsp.CloneAPIBookMyShow.enums.ScreenAvailability;
import com.jsp.CloneAPIBookMyShow.enums.ScreenStatus;
import com.jsp.CloneAPIBookMyShow.enums.ScreenType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenDTO {

	private long screenId;
	
	@NotBlank(message  = "screen name not be empty...")
	@Pattern(regexp = "[\\w\\s]*" ,message = "Invalid Screen_Name..")
	private String screenName;
	
//	SCREEN TYPE
	private ScreenType screenType; 
//	SCREEN AVAILABILITY[we can get this filed data at update time from frontEnd]
	private ScreenAvailability screenAvailable;
//	SCREEN STATUS[we can get this field data at update time from frontEnd]
	private ScreenStatus screenStatus;
	
	//BELOW FIELDS GET AT SAVE TIME ONLY
	@NotNull@Min(value = 0, message = "invalid no of seats..")
	private int noOfClassicSeats;
	
	@NotNull@Min(value = 0, message = "invalid no of seats..")
	private int noOfPlatinumSeats;
	
	@NotNull@Min(value = 0, message = "invalid no of seats..")
	private int noOfGoldSeats;
		
}