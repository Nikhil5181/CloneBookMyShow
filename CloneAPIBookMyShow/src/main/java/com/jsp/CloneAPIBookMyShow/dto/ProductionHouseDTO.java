package com.jsp.CloneAPIBookMyShow.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionHouseDTO {

	private long productionHouseId;
	
	@NotBlank@Pattern(regexp = "[a-zA-z][\\w\\s]*" , message = "Invalid production_house Name..")
	private String productionHouseName;
	
	@NotNull(message = "Eshtablishment date not be empty...")
	private LocalDate eshtablishment;
	
}
