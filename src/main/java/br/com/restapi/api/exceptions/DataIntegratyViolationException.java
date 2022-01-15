package br.com.restapi.api.exceptions;

public class DataIntegratyViolationException extends RuntimeException{

	public DataIntegratyViolationException(String message) {
		super(message);
	}

}
