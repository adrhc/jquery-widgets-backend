package ro.go.adrhc.datarest.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ro.go.adrhc.datarest.entities.Person;

import java.util.List;

public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {
	List<Person> findByFirstNameStartingWith(String firstName);
}
