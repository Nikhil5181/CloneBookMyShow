package com.jsp.CloneAPIBookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {

	private long ownerId;
	private String ownerName;
	private long ownerPhoneNumber;
	private String ownerEmail;
	
}