package br.com.restapi.api.exceptions;

@SuppressWarnings("serial")
public class ObjectNotFoundException extends RuntimeException{

	public ObjectNotFoundException(String message) {
		super(message);
	}

}
