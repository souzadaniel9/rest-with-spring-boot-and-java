package br.com.daniel.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniel.exceptions.ResourceNotFoundException;
import br.com.daniel.model.Person;
import br.com.daniel.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public List<Person> findAll() {
		return repository.findAll();
	}

	public Optional<Person> findById(Long id) {
		Optional<Person> p = repository.findById(id);

		if (p.isEmpty()) {
			throw new ResourceNotFoundException("ID não encontrado");
		}

		return p;
	}

	public Person adicionar(Person person) {
		return repository.save(person);
	}

	public void atualizar(Person person, Long id) {

		Optional<Person> newPerson = repository.findById(id);

		newPerson.get().setFirstName(person.getFirstName());
		newPerson.get().setLastName(person.getLastName());
		newPerson.get().setAddress(person.getAddress());
		newPerson.get().setGender(person.getGender());

		repository.save(newPerson.get());
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
