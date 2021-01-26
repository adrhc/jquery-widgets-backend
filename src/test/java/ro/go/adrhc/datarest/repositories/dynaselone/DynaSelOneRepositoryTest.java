package ro.go.adrhc.datarest.repositories.dynaselone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import ro.go.adrhc.datarest.config.SpringJpaTest;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.PersonsRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJpaTest
@ComponentScan(basePackageClasses = {DynaSelOneRepository.class})
class DynaSelOneRepositoryTest {
	@Autowired
	private PersonsRepository repository;
	@Autowired
	private DynaSelOneRepository dynaSelOneRepository;

	@Test
	void findByTitle() {
		Person person = new Person(1, "gigi", "gigi", null, null);
		repository.save(person);
		List<Person> persons = dynaSelOneRepository.findByTitle("gigi", "person");
		assertThat(persons).containsOnly(person);
	}
}
