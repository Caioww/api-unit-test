package br.com.restapi.api.resources.exceptions;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.restapi.api.exceptions.DataIntegrityViolationException;
import br.com.restapi.api.exceptions.ObjectNotFoundException;

@SpringBootTest
public class ResourceExceptionHandlerTest {
	
	@InjectMocks
	private ResourceExceptionHandler exceptionHandler;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void whenObjectNotFoundExceptionThenReturnAnResponseEntity() {
		ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(new ObjectNotFoundException("Objeto não encontrado"), new MockHttpServletRequest());
		
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertNotEquals("/user/2", response.getBody().getPath());
		Assertions.assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
	}
	
	@Test
	void whenDataIntegrityViolationException() {
		ResponseEntity<StandardError> response = exceptionHandler.dataIntegratyViolationException(new DataIntegrityViolationException("E-mail já cadastrado"), new MockHttpServletRequest());
		
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}

}
