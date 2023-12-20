package br.com.daniel.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.DTO.PersonDto;
import br.com.daniel.model.Person;

public class MockPerson {

	public Person mockEntity() {
		return mockPerson(0);
	}

	public PersonDto mockDto() {
		return mockDto(0);
	}

	public List<Person> mockEntityList() {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockPerson(i));
		}
		return persons;
	}

	public List<PersonDto> mockDtoList() {
		List<PersonDto> persons = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockDto(i));
		}
		return persons;
	}

	public Person mockPerson(Integer number) {
		Person person = new Person();
		person.setAddress("Addres Test" + number);
		person.setFirstName("First Name Test" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		person.setId(number.longValue());
		person.setLastName("Last Name Test" + number);
		return person;
	}

	public PersonDto mockDto(Integer number) {
		PersonDto person = new PersonDto();
		person.setAddress("Addres Test" + number);
		person.setFirstName("First Name Test" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		person.setId(number.longValue());
		person.setLastName("Last Name Test" + number);
		return person;
	}

}