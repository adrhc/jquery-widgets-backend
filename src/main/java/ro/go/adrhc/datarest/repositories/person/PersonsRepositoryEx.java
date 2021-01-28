package ro.go.adrhc.datarest.repositories.person;

import ro.go.adrhc.datarest.dto.PersonDto;
import ro.go.adrhc.datarest.entities.Person;

import java.util.List;

public interface PersonsRepositoryEx {
	List<PersonDto> findAllDto();

	PersonDto loadDtoById(Integer id);

	Person loadInitializedById(Integer id);

	Person insert(Person person);

	Person merge(Person person);

	Person insertOrUpdate(Person person);
}
