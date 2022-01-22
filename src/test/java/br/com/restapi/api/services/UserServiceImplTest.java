package br.com.restapi.api.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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

import br.com.restapi.api.domain.User;
import br.com.restapi.api.domain.dto.UserDto;
import br.com.restapi.api.exceptions.DataIntegrityViolationException;
import br.com.restapi.api.exceptions.ObjectNotFoundException;
import br.com.restapi.api.repositories.UserRepository;

@SpringBootTest
public class UserServiceImplTest {
	
	private static final String PASSWORD = "1234";

	private static final String EMAIL = "caio@gmail.com";

	private static final String NAME = "Caio";

	private static final int ID = 1;
	

	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;
	
    @Mock
	private ModelMapper mapper;
	
	private User user;
	
	private UserDto userDTO;
	
	private Optional<User> optionalUser;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
		
	}
	
	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
		User response = service.findById(ID);
		Assertions.assertEquals(User.class, response.getClass());
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		
		try {
			service.findById(ID);
		
		}catch(Exception ex) {
			Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
		}
	}
	
	@Test
	void WhenFindAllThenReturnAnListOfUsers() {
		when(repository.findAll()).thenReturn(Arrays.asList(user));
		List<User> response = service.findAll();
		
		Assertions.assertEquals(1, response.size());
		
	}
	
	@Test
	void whenCreateThenReturnAnDataIntegrityViolationException() {
		when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		}catch(Exception ex) {
			Assertions.assertEquals(DataIntegrityViolationException.class, ex.getClass());
		}
		
	
	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(repository.save(Mockito.any())).thenReturn(user);
		
		User response = service.create(userDTO);
		
		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertNotNull(response);
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(repository.save(Mockito.any())).thenReturn(user);
		
		User response = service.update(userDTO);
		
		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertNotNull(response);
	}
	
	@Test
	void whenUpdateThenReturnDataIntegrityViolationException() {
		when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		}catch(Exception ex) {
			Assertions.assertEquals(DataIntegrityViolationException.class, ex.getClass());
		}
		
	
	}
	
	@Test
	void whenDeleteThenReturnSuccess() {
		when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
		Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
		service.delete(ID);
		
		verify(repository, times(1)).deleteById(Mockito.anyInt());
	}
	
	@Test
	void whenDeleteThenReturnObjectNotFoundException() {
		when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

		try {
			service.delete(ID);
		}catch(Exception ex) {
			Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
		}
	}
	
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDto(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}
	
	

}
