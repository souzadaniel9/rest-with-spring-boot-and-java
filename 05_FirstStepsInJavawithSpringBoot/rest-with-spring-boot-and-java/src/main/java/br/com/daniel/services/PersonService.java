package br.com.daniel.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniel.DTO.PersonDto;
import br.com.daniel.controller.PersonController;
import br.com.daniel.exceptions.ResourceNotFoundException;
import br.com.daniel.model.Person;
import br.com.daniel.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public List<PersonDto> findAll() {
		List<Person> person = repository.findAll();
		 var persons = person.stream().map(perso -> new ModelMapper().map(perso, PersonDto.class))
				.collect(Collectors.toList());
		 
		 persons.stream()
		 		.forEach(p -> p.add(linkTo(methodOn(PersonController.class)
		 		.findById(p.getId())).withSelfRel()));
		 
		 return persons;
	}

	public PersonDto findById(Long id) {
		Person person = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));

		PersonDto personDto = new ModelMapper().map(person, PersonDto.class);
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return personDto;
	}

	public PersonDto criar(PersonDto personDto) {
		Person person = new ModelMapper().map(personDto, Person.class);
		repository.save(person);
		personDto.setId(person.getId());
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getId())).withSelfRel());
		
		return personDto;
	}

	public void atualizar(PersonDto personDto, Long id) {
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
		BeanUtils.copyProperties(personDto, person, "id");

		personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getId())).withSelfRel());
		
		repository.save(person);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
