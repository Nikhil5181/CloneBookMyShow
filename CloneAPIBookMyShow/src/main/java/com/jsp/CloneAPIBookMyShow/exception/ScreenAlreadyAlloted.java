package com.jsp.CloneAPIBookMyShow.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ScreenAlreadyAlloted extends RuntimeException {

	private String message;
	
	@Override
	public String toString() {
		return message;
	}
	
}
