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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Theatre {
	 
	@Id
  	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long theatreId;
	private String theaterName;
	
	@OneToOne
	@JoinColumn
	private Address address;
	
	@OneToMany(cascade = CascadeType.REMOVE , mappedBy = "theatre") 
	@JsonIgnore
	private List<Screen> screens;
	
	@ManyToOne
	@JoinColumn
	private Owner owner;
	
	@OneToMany(mappedBy = "theatre" ,cascade = CascadeType.REMOVE)
	private List<MovieShow> show;
}