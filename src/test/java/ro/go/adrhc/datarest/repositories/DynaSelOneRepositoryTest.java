package ro.go.adrhc.datarest.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.entities.Person;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
class DynaSelOneRepositoryTest {
	@Autowired
	private DynaSelOneRepository repository;
	@Autowired
	private EntityManager em;

	@Test
	@Transactional
	void findByTitle() {
		em.persist(Person.builder().firstName("gigi").lastName("gigi").build());
		List<Person> persons = repository.findByTitle("gigi", "person");
		log.debug("persons:\n{}", persons.stream().map(Person::toString).collect(Collectors.joining("\n")));
	}
}
