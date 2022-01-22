package br.com.restapi.api.resources;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.restapi.api.domain.User;
import br.com.restapi.api.domain.dto.UserDto;
import br.com.restapi.api.services.UserServiceImpl;

@SpringBootTest
public class UserResourceTest {
	
	private static final String PASSWORD = "1234";

	private static final String EMAIL = "caio@gmail.com";

	private static final String NAME = "Caio";

	private static final int ID = 1;
	
	@InjectMocks
	private UserResource resource;
	
	@Mock
	private UserServiceImpl service;
	
	@Mock
	private ModelMapper mapper;
	
	private User user;
	
	private UserDto userDTO = new UserDto();
	
	private Optional<User> optionalUser;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnSuccess() {
		when(service.findById(Mockito.anyInt())).thenReturn(user);
		when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		
		ResponseEntity<UserDto> response = resource.findById(ID);
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
	}
	
	@Test
	void whenFindAllThenReturnSuccess() {
		
		List<User> listUsers = new ArrayList<>();
		listUsers.add(user);
		
		when(service.findAll()).thenReturn(listUsers);
		when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDto>> response = resource.findAll();
		Assertions.assertNotNull(response);

	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(service.create(Mockito.any())).thenReturn(user);
		ResponseEntity<UserDto> response = resource.create(userDTO);
		
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(service.update(Mockito.any())).thenReturn(user);
		when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		
		ResponseEntity<UserDto> response = resource.update(ID, userDTO);
		
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response);

		
		
	}
	
	@Test
	void whenDeleteThenReturnSuccess() {
		Mockito.doNothing().when(service).delete(Mockito.anyInt());
		
		ResponseEntity<UserDto> response = resource.delete(ID);
		
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Assertions.assertNotNull(response);
		Assertions.assertNull(response.getBody());
		verify(service, Mockito.times(1)).delete(Mockito.anyInt());

		
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDto(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}

}
