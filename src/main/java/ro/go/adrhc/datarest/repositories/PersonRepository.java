package ro.go.adrhc.datarest.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ro.go.adrhc.datarest.entities.Person;

@CrossOrigin
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {}
