package com.jsp.CloneAPIBookMyShow.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long addressId;
	private int flatNo;
	private String area;
	private String landmark;
	private String city;
	private String state;
	private String country;
	private long pincode;
	
	@OneToOne(mappedBy = "address")
	@JsonIgnore
	private Theatre theatre;
	
	
	@Override
	public String toString() {
		return "FlatNo :"+flatNo+","+
				"Area :"+area+","+
				"Landmark :"+landmark+","+
				"City :"+city+","+
				"State :"+state+","+
				"Country :"+country+","+
				"Pincode :"+pincode;
	}
}
