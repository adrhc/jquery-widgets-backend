package ro.go.adrhc.datarest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.entities.Person;

import java.util.List;

@Transactional
public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {
	@Query("from Person p join fetch p.cats")
	List<Person> findAll();

	List<Person> findByFirstNameStartingWith(String firstName);
}
