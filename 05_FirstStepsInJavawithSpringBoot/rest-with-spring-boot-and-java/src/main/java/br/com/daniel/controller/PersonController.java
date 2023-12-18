package br.com.daniel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniel.DTO.PersonDto;
import br.com.daniel.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/person")
public class PersonController {
						
	@Autowired
	PersonService service;

	@Operation(summary = "Find a specific person by your ID")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
		PersonDto personDto = service.findById(id);
		return new ResponseEntity<>(personDto, HttpStatus.OK);
	}

	@Operation(summary = "Find all people")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<PersonDto>> findAll() {
		List<PersonDto> list = service.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Operation(summary = "Create a new person")
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
				 consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<PersonDto> adicionar(@RequestBody PersonDto personDto) {
		service.criar(personDto);
		return new ResponseEntity<>(personDto, HttpStatus.CREATED);
	}

	@PutMapping(
				produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
				consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<PersonDto> atualizar(@RequestBody PersonDto personDto) {
		return new ResponseEntity<>(service.atualizar(personDto), HttpStatus.OK);
	}

	@Operation(summary = "Delete specific person by your ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {

		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
