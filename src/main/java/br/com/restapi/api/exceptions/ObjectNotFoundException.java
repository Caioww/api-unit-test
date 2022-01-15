package br.com.restapi.api.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	public ObjectNotFoundException(String message) {
		super(message);
	}

}
