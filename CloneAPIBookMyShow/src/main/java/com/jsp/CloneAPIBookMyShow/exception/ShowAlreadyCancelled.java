package com.jsp.CloneAPIBookMyShow.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ShowAlreadyCancelled extends RuntimeException {

	private String message;

	@Override
	public String getMessage(){
		return message;
	}
}
