package ro.go.adrhc.datarest.repositories;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.dto.PersonDto;
import ro.go.adrhc.datarest.entities.Cat;
import ro.go.adrhc.datarest.entities.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@NotThreadSafe
class PersonsRepositoryTest {
	@Autowired
	private PersonsRepository repository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * How to map a one-to-many with JdbcTemplate:
	 * https://arnaudroger.github.io/blog/2017/06/13/jdbc-template-one-to-many.html
	 * <p>
	 * Propagation.NOT_SUPPORTED - otherwise cat.person_id will be null
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Test
	void findAll() {
		repository.save(new Person("gigi", "gigi", null, List.of(new Cat("cat1"))));
		List<PersonDto> persons = repository.findAllDto();
		log.debug("persons:\n{}", persons.stream().map(PersonDto::toString).collect(Collectors.joining("\n")));
		assertThat(persons).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("firstName", "gigi");
		assertThat(persons.get(0).getCats()).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("personId", 1);
	}

	/**
	 * Propagation.NOT_SUPPORTED - otherwise repository will commit after jdbcTemplate execution
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Test
	void repositorySave() {
		repository.save(new Person("gigi", "gigi", null, List.of(new Cat("cat1"))));
		List<Person> persons = jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
		log.debug("persons:\n{}", persons.stream().map(Person::toString).collect(Collectors.joining("\n")));
		assertThat(persons).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("firstName", "gigi");
		List<Cat> cats = jdbcTemplate.query("SELECT * FROM cat", new BeanPropertyRowMapper<>(Cat.class));
		log.debug("cats:\n{}", cats.stream().map(Cat::toString).collect(Collectors.joining("\n")));
		assertThat(cats).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("name", "cat1")
				.hasFieldOrPropertyWithValue("personId", 1);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Test
	void saveWithFriend() {
		Person person = repository.save(new Person("gigi", "gigi",
				new Person("gigi", "gigi", null, List.of(new Cat("cat1"))),
				List.of(new Cat("cat1"))));
		Optional<Person> optional = repository.findById(person.getId());
		assertThat(optional).isPresent().map(Person::getFriend).isNotEmpty();
	}
}
