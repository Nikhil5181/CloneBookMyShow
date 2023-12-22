package com.jsp.CloneAPIBookMyShow.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TheatreNotFoundException extends RuntimeException {

	String message;
	
	@Override
	public String getMessage() {
		return message;
	}
}
