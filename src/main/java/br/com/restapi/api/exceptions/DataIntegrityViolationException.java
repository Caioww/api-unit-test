package br.com.restapi.api.exceptions;

@SuppressWarnings("serial")
public class DataIntegrityViolationException extends RuntimeException{

	public DataIntegrityViolationException(String message) {
		super(message);
	}

}
