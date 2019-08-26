package com.jabreu.exception;

public class NoTimeLeftException extends RuntimeException {

	private static final long serialVersionUID = -5515298997115111858L;

	@Override
	public String getMessage() {
		return "There isn't time left to schedule event.";
	}
	
}
