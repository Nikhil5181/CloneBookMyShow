package com.jsp.CloneAPIBookMyShow.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.jsp.CloneAPIBookMyShow.enums.Genre;

import lombok.Data;

@Entity
@Data
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long movieId;
	private String movieName;
	
	//generess Enum
	private Genre genre1;
	private Genre genre2;
	private Genre genre3;
	@DateTimeFormat(style = "HH:mm")
	private LocalTime movieDuration;
	private String movieDescription;
	private String language;
	
	@ManyToOne
	@JoinColumn
	private ProductionHouse productionHouse;
	
}
