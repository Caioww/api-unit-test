package br.com.restapi.api.services;

import java.util.List;

import br.com.restapi.api.domain.User;
import br.com.restapi.api.domain.dto.UserDto;

public interface UserService{
	
	User findById(Integer id);
	
	List<User> findAll();
	
	User create(UserDto obj);
	
	User update(UserDto obj);
	
	void delete(Integer id);

}
