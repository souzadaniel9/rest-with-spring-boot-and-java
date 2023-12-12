package br.com.daniel.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniel.DTO.PersonDto;
import br.com.daniel.exceptions.ResourceNotFoundException;
import br.com.daniel.model.Person;
import br.com.daniel.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public List<PersonDto> findAll() {
		List<Person> person = repository.findAll();
		return person.stream().map(persons -> new ModelMapper().map(persons, PersonDto.class))
			         .collect(Collectors.toList());
	}

	public Optional<PersonDto> findById(Long id) {
		Optional<Person> person = repository.findById(id);

		if (person.isEmpty()) {
			throw new ResourceNotFoundException("ID não encontrado");
		}

		PersonDto personDto = new ModelMapper().map(person, PersonDto.class);
		 return Optional.of(personDto);
	}

	public PersonDto adicionar(PersonDto personDto) {
		Person p = new ModelMapper().map(personDto, Person.class);

		repository.save(p);
		
		personDto.setId(p.getId());
		
		return personDto;
	}

	public void atualizar(PersonDto personDto, Long id) {
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));

		BeanUtils.copyProperties(personDto, person, "id");
		
		repository.save(person);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
