package ro.go.adrhc.datarest.repositories.dynaselone;

import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.person.PersonsRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@NotThreadSafe
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
