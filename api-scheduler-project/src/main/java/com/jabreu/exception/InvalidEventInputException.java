package com.jabreu.exception;

public class InvalidEventInputException extends RuntimeException {

	private static final long serialVersionUID = -8134816011663895398L;

	@Override
	public String getMessage() {
		return "This input is not a valid event.";
	}
}
