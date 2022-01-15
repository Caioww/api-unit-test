package br.com.restapi.api.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restapi.api.domain.User;
import br.com.restapi.api.domain.dto.UserDto;
import br.com.restapi.api.exceptions.DataIntegratyViolationException;
import br.com.restapi.api.exceptions.ObjectNotFoundException;
import br.com.restapi.api.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
		
	}
	
	public List<User> findAll(){
		return repository.findAll();
	}

	@Override
	public User create(UserDto obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}
	

	@Override
	public User update(UserDto obj) {
		findByEmail(obj);		
		return repository.save(mapper.map(obj, User.class));
	}
	
	private void findByEmail(UserDto obj) {
		Optional<User> user = repository.findByEmail(obj.getEmail());
		if(user.isPresent() && !user.get().getId().equals(obj.getId())) {
			throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
		}
	}

	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
		
	}

}
