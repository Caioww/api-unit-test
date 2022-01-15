package br.com.restapi.api.services;

import br.com.restapi.api.domain.User;

public interface UserService{
	
	User findById(Integer id);

}
