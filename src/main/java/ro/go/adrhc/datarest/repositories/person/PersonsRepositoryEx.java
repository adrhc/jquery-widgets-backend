package ro.go.adrhc.datarest.repositories.person;

import ro.go.adrhc.datarest.dto.PersonDto;
import ro.go.adrhc.datarest.entities.Person;

import java.util.List;

public interface PersonsRepositoryEx {
	List<Person> findAll();

	PersonDto loadDtoById(Integer id);
}
