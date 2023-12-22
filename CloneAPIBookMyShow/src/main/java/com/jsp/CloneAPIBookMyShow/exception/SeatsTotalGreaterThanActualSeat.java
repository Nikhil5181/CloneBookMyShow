package com.jsp.CloneAPIBookMyShow.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SeatsTotalGreaterThanActualSeat extends RuntimeException {
	
	private String message;
	
	@Override
	public String getMessage() {
		return message;
	}

}
