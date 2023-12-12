package br.com.daniel.controller;

import java.util.List;
import java.util.Optional;

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
import br.com.daniel.exceptions.ResourceNotFoundException;
import br.com.daniel.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService service;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Optional<PersonDto>> findById(@PathVariable Long id) {
		Optional<PersonDto> p = service.findById(id);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PersonDto>> findAll() {
		List<PersonDto> list = service.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PersonDto> adicionar(@RequestBody PersonDto person) {
		service.adicionar(person);
		return new ResponseEntity<>(person, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public void atualizar(@PathVariable Long id, @RequestBody PersonDto person) {
		service.atualizar(person, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {

		if (service.findById(id).isEmpty()) {
			new ResourceNotFoundException("ID não encontrado");
		}

		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
