package com.jsp.CloneAPIBookMyShow.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductionHouse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productionHouseId;
	private String productionHouseName;
	
	private LocalDate eshtablishment;
	
	@ManyToOne
	@JoinColumn
	private Owner owner;
	
	@OneToMany(mappedBy = "productionHouse" , cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Movie> movies;
	
}
