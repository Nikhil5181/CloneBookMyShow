package com.jsp.CloneAPIBookMyShow.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ShowHasAlreadyStrated extends RuntimeException {

	private String message;
	
	@Override
	public String getMessage() {
		return message;
	}
}
