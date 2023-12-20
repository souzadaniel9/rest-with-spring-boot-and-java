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
import br.com.daniel.exceptions.RequiredObjectIsNullException;
import br.com.daniel.exceptions.ResourceNotFoundException;
import br.com.daniel.model.Person;
import br.com.daniel.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public PersonDto findById(Long id) {
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
		
		PersonDto personDto = new ModelMapper().map(person, PersonDto.class);
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return personDto;
	}
	
	public List<PersonDto> findAll() {
		List<Person> person = repository.findAll();
		 var persons = person.stream().map(
				 perso -> new ModelMapper().map(perso, PersonDto.class)).collect(Collectors.toList());
		 
		 persons.stream()
		 		.forEach(p -> p.add(linkTo(methodOn(PersonController.class)
		 		.findById(p.getId())).withSelfRel()));
		 
		 return persons;
	}


	public PersonDto newPerson(PersonDto personDto) {
		if(personDto == null) throw new RequiredObjectIsNullException();
		
		Person person = new ModelMapper().map(personDto, Person.class);
		repository.save(person);
		personDto.setId(person.getId());
		
		personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getId())).withSelfRel());
		
		return personDto;
	}

	public PersonDto update(PersonDto personDto) {
		if(personDto == null) throw new RequiredObjectIsNullException();
		var person = repository.findById(personDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
		
		BeanUtils.copyProperties(personDto, person);
		PersonDto newPerson = new ModelMapper().map(repository.save(person), PersonDto.class);
		newPerson.add(linkTo(methodOn(PersonController.class).findById(newPerson.getId())).withSelfRel());
		return newPerson;
	}

	public void delete(Long id) {
		repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
		repository.deleteById(id);
	}
}
