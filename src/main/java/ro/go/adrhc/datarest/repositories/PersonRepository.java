package ro.go.adrhc.datarest.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ro.go.adrhc.datarest.entities.Person;

import java.util.List;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
	List<Person> findByFirstName(String firstName);
}
