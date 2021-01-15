package ro.go.adrhc.datarest.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.person.PersonsRepositoryEx;

import java.util.List;

public interface PersonsRepository extends PersonsRepositoryEx, PagingAndSortingRepository<Person, Integer> {
	List<Person> findByFirstNameStartingWith(String firstName);
}
