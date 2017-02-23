package ca.mcgill.ecse223.tileo.controller;

public class InvalidInputException extends Exception {
	public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}
}
