package br.com.restapi.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.restapi.api.domain.User;
import br.com.restapi.api.domain.dto.UserDto;
import br.com.restapi.api.services.UserService;

@RestController
@RequestMapping(value="/user")
public class UserResource {

	
	private static final String ID = "/{id}";

	@Autowired 
	private ModelMapper mapper;
	
	@Autowired
	private UserService service;
	
	@GetMapping(value=ID)
	public ResponseEntity<UserDto> findById(@PathVariable Integer id){
		return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDto.class));
		 
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> findAll(){
		List<UserDto> listDTO = service.findAll().stream().map(x -> mapper.map(x, UserDto.class)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<UserDto> create(@RequestBody UserDto userDTO){
		User newObj = service.create(userDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(newObj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
	@PutMapping(value = ID)
	public ResponseEntity<UserDto> update(@PathVariable Integer id, @RequestBody UserDto obj){
		obj.setId(id);
		User newObj = service.update(obj);
		return ResponseEntity.ok().body(mapper.map(newObj, UserDto.class));
		
		
	}
	
	@DeleteMapping(value = ID)
	public ResponseEntity<UserDto> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
		
	}


}
