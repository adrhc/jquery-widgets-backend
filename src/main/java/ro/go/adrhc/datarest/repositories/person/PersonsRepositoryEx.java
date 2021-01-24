package ro.go.adrhc.datarest.repositories.person;

import ro.go.adrhc.datarest.dto.PersonDto;

import java.util.List;

public interface PersonsRepositoryEx {
	List<PersonDto> findAllDto();

	PersonDto loadDtoById(Integer id);
}
