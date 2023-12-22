package com.jsp.CloneAPIBookMyShow.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
	
	private long addressId;
	
	@NotNull(message = "Flat no can't be empty..")
	@Min(value = 1)
	private int flatNo;
	
	@NotBlank(message = "Area can't be empty..")
	private String area;
	
	@NotBlank(message = "Landmark can't be empty..")
	private String landmark;
	
	@Pattern(regexp = "[a-zA-Z\\s]*", message = "Invalid City Name..." )
	private String city;
	
	@Pattern(regexp = "[a-zA-Z\\s]*", message = "Invalid State Name..")
	private String state;
	
	@Pattern(regexp = "[a-zA-Z\\s]*", message = "Invalid Country Name..")
	private String country;
	
	@NotNull(message = "Pincode can't be empty...")
	@Min(value = 111111 , message = "invalid pincode")
	private long pincode;
}
