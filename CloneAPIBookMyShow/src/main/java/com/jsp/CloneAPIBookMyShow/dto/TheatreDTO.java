package com.jsp.CloneAPIBookMyShow.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreDTO {

	private long theatreId;
	
	@NotBlank(message = "Theatre name not be empty...")
	@Pattern(regexp = "[a-zA-z][a-zA-Z\\s]*", message = "Invalid Theatre Name..")
	private String theaterName;
	
}