package br.com.restapi.api.config;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.restapi.api.domain.User;
import br.com.restapi.api.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {
	
	@Autowired
	private UserRepository repository;

	
	@Bean
	public void startDB() {
		User u1 = new User(null, "Caio", "caio@gmai.com", "123");
		
		repository.save(u1);
	}

}
