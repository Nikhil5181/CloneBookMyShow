package com.jsp.CloneAPIBookMyShow.dto;

import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.jsp.CloneAPIBookMyShow.enums.Genre;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {

	private long movieId;
	
	@NotBlank(message = "Movie_Name can't be empty..")
	@Pattern(regexp = "[\\w\\s]*" ,message = "Invalid Name")
	private String movieName;
	
	//GENERE
	private Genre genre1;
	private Genre genre2;
	private Genre genre3;
	
	@NotNull@DateTimeFormat(style = "HH:mm")
	private LocalTime movieDuration;
	@NotBlank
	private String movieDescription;
	
	@NotBlank(message = "Movie_language can't be empty..")
	@Pattern(regexp = "[a-zA-Z\\s]*" ,message = "Only contains character fo language..")
	private String language;
		
}