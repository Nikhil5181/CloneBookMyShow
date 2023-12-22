package com.jsp.CloneAPIBookMyShow.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TicketAlreadyExpired extends RuntimeException {
	
	private String message;
	
	public String getMessage() {
		return message;
	}

}
