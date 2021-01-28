package ro.go.adrhc.datarest.repositories;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.Disabled;
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
import ro.go.adrhc.datarest.repositories.person.PersonsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@NotThreadSafe
@Transactional(propagation = Propagation.NEVER)
class PersonsRepositoryIT {
	@Autowired
	private CatsRepository catsRepository;
	@Autowired
	private PersonsRepository personsRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * How to map a one-to-many with JdbcTemplate:
	 * https://arnaudroger.github.io/blog/2017/06/13/jdbc-template-one-to-many.html
	 * <p>
	 * Propagation.NOT_SUPPORTED - otherwise cat.person_id will be null
	 */
	@Test
	void findAllDto() {
		personsRepository.save(new Person("gigi", "gigi", null, List.of(new Cat("cat1"))));
		List<PersonDto> persons = personsRepository.findAllDto();
		log.debug("persons:\n{}", persons.stream().map(PersonDto::toString).collect(Collectors.joining("\n")));
		assertThat(persons).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("firstName", "gigi");
		assertThat(persons.get(0).getCats()).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("name", "cat1");
	}

	/**
	 * Propagation.NOT_SUPPORTED - otherwise repository will commit after jdbcTemplate execution
	 */
	@Test
	void save() {
		personsRepository.save(new Person("gigi", "gigi", null, List.of(new Cat("cat1"))));
		List<Person> persons = jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
		log.debug("persons:\n{}", persons.stream().map(Person::toString).collect(Collectors.joining("\n")));
		assertThat(persons).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("firstName", "gigi");
		List<Cat> cats = jdbcTemplate.query("SELECT * FROM cat", new BeanPropertyRowMapper<>(Cat.class));
		log.debug("cats:\n{}", cats.stream().map(Cat::toString).collect(Collectors.joining("\n")));
		assertThat(cats).hasSize(1).element(0)
				.hasFieldOrPropertyWithValue("name", "cat1")
				.hasFieldOrPropertyWithValue("ownerId", 1);
	}

	@Test
	void saveWithFriend() {
		Person person = personsRepository.save(new Person("gigi", "gigi",
				new Person("gigi", "gigi", null, List.of(new Cat("cat1"))),
				List.of(new Cat("cat1"))));
		Optional<Person> optional = personsRepository.findById(person.getId());
		assertThat(optional).isPresent().map(Person::getFriend).isNotEmpty();
	}

	/**
	 * friend use CascadeType.PERSIST only
	 * <p>
	 * firstName + cats changes + transient friend (only the id is saved)
	 */
	@Test
	@Disabled
	void mergeByAdding1TransientCatAndKeep1AnotherAndTransientFriend1() {
		Person person = createThenReloadPerson();
		log.debug("person:\n{}", person);

		person.setFirstName(person.getFirstName() + "-changed");
		person.setCats(List.of(new Cat(person.getCats().get(1).getId(),
				person.getCats().get(1).getName()), new Cat("cat3")));
		person.setFriend(new Person("friend2", "friend2"));
		// equivalent to entityManager.merge(person)
		person = personsRepository.save(person);

		// solving: person.friend.cats: failed to lazily initialize a collection
		log.debug("person:\n{}", personsRepository.loadInitializedById(person.getId()));
		assertThat(person.getFirstName()).isEqualTo("gigi-changed");
		assertThat(person.getCats().get(1).getName()).isEqualTo("cat3");
		assertThat(person.getFriend().getId()).isNotNull();
		assertThat(person.getFriend().getFirstName()).isNull();
	}

	/**
	 * friend use CascadeType.PERSIST only
	 * <p>
	 * firstName + cats changes + detached friend (name doesn't change)
	 */
	@Test
	@Disabled
	void mergeByAdding1TransientCatAndKeep1AnotherAndDetachedFriend2() {
		Person person = createThenReloadPerson();
		log.debug("person:\n{}", person);

		person.setFirstName(person.getFirstName() + "-changed");
		person.setCats(List.of(new Cat(person.getCats().get(1).getId(),
				person.getCats().get(1).getName()), new Cat("cat3")));
		Person friend2 = personsRepository.insert(new Person("friend2", "friend2"));
		friend2.setFirstName(friend2.getFirstName() + "-changed");
		person.setFriend(friend2);
		// same result as with person.setFriend(friend2):
//		person.setFriend(new Person(friend2.getId()));
		// equivalent to entityManager.merge(person)
		person = personsRepository.save(person);

		// solving: person.friend.cats: failed to lazily initialize a collection
		log.debug("person:\n{}", personsRepository.loadInitializedById(person.getId()));
		assertThat(person.getCats().get(1).getName()).isEqualTo("cat3");
		assertThat(person.getFirstName()).isEqualTo("gigi-changed");
		assertThat(person.getFriend().getFirstName()).isEqualTo("friend2");
	}

	/**
	 * friend use CascadeType.PERSIST only
	 * <p>
	 * firstName + cats changes + same friend (name doesn't change)
	 */
	@Test
	@Disabled
	void mergeByAdding1TransientCatAndKeep1Another1() {
		Person person = createThenReloadPerson();
		log.debug("person:\n{}", person);

		person.setFirstName(person.getFirstName() + "-changed");
		person.getFriend().setFirstName(person.getFriend().getFirstName() + "-changed");
		person.setCats(List.of(new Cat(person.getCats().get(1).getId(),
				person.getCats().get(1).getName()), new Cat("cat3")));
		// equivalent to entityManager.merge(person)
		person = personsRepository.save(person);

		// solving: person.friend.cats: failed to lazily initialize a collection
		log.debug("person:\n{}", personsRepository.loadInitializedById(person.getId()));
		assertThat(person.getCats().get(1).getName()).isEqualTo("cat3");
		assertThat(person.getFirstName()).isEqualTo("gigi-changed");
		assertThat(person.getFriend().getFirstName()).isEqualTo("friend1");
	}

	/**
	 * friend use CascadeType.PERSIST + CascadeType.MERGE
	 * <p>
	 * firstName + cats changes + new transient friend
	 */
	@Test
	void mergeByAdding1TransientCatAndKeep1AnotherAndTransientFriend2() {
		Person person = createThenReloadPerson();
		log.debug("person:\n{}", person);

		person.setFirstName(person.getFirstName() + "-changed");
		person.setCats(List.of(new Cat(person.getCats().get(1).getId(),
				person.getCats().get(1).getName()), new Cat("cat3")));
		person.setFriend(new Person("friend2", "friend2"));
		// equivalent to entityManager.merge(person)
		person = personsRepository.save(person);

		// solving: person.friend.cats: failed to lazily initialize a collection
		log.debug("person:\n{}", personsRepository.loadInitializedById(person.getId()));
		assertThat(person.getFirstName()).isEqualTo("gigi-changed");
		assertThat(person.getCats().get(1).getName()).isEqualTo("cat3");
		assertThat(person.getFriend().getFirstName()).isEqualTo("friend2");
	}

	/**
	 * friend use CascadeType.PERSIST + CascadeType.MERGE + cats initialized with new ArrayList<>()
	 * <p>
	 * firstName + cats changes + detached new friend (name does change)
	 * <p>
	 * cats initialized with new ArrayList<>() solves:
	 * JpaSystemException: A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: ro.go.adrhc.datarest.entities.Person.cats
	 * see https://stackoverflow.com/questions/5587482/hibernate-a-collection-with-cascade-all-delete-orphan-was-no-longer-referenc
	 */
	@Test
	void mergeByAdding1TransientCatAndKeep1AnotherAndDetachedFriend1() {
		Person person = createThenReloadPerson();
		log.debug("person:\n{}", person);

		person.setFirstName(person.getFirstName() + "-changed");
		person.setCats(List.of(new Cat(person.getCats().get(1).getId(),
				person.getCats().get(1).getName()), new Cat("cat3")));
		Person friend2 = personsRepository.insert(new Person("friend2", "friend2"));
		friend2.setFirstName(friend2.getFirstName() + "-changed");
		person.setFriend(friend2);
		// same issue as with person.setFriend(friend2):
//		person.setFriend(new Person(friend2.getId(), "friend2", "friend2"));
		// equivalent to entityManager.merge(person)
		person = personsRepository.save(person);

		// solving: person.friend.cats: failed to lazily initialize a collection
		log.debug("person:\n{}", personsRepository.loadInitializedById(person.getId()));
		assertThat(person.getFirstName()).isEqualTo("gigi-changed");
		assertThat(person.getCats().get(1).getName()).isEqualTo("cat3");
		assertThat(person.getFriend().getFirstName()).isEqualTo("friend2-changed");
	}

	/**
	 * friend use CascadeType.PERSIST + CascadeType.MERGE
	 * <p>
	 * firstName + cats changes + detached same friend (name does change)
	 */
	@Test
	void mergeByAdding1TransientCatAndKeep1Another2() {
		Person person = createThenReloadPerson();
		log.debug("person:\n{}", person);

		person.setFirstName(person.getFirstName() + "-changed");
		person.getFriend().setFirstName(person.getFriend().getFirstName() + "-changed");
		person.setCats(List.of(new Cat(person.getCats().get(1).getId(),
				person.getCats().get(1).getName()), new Cat("cat3")));
		// equivalent to entityManager.merge(person)
		person = personsRepository.save(person);

		// solving: person.friend.cats: failed to lazily initialize a collection
		log.debug("person:\n{}", personsRepository.loadInitializedById(person.getId()));
		assertThat(person.getCats().get(1).getName()).isEqualTo("cat3");
		assertThat(person.getFirstName()).isEqualTo("gigi-changed");
		assertThat(person.getFriend().getFirstName()).isEqualTo("friend1-changed");
	}


	/**
	 * friend use CascadeType.PERSIST + CascadeType.MERGE
	 * <p>
	 * new person + detached friend (name does change)
	 */
	@Test
	void newPersonWithDetachedFriend() {
		Cat catX = catsRepository.save(new Cat("catX"));
		Person person = personsRepository.insert(generatePerson());
		person.getCats().add(catX);
		Person friend1 = personsRepository.insert(new Person("friend1", "friend1"));
		friend1.setFirstName(friend1.getFirstName() + "-changed");
		person.setFriend(friend1);
		person = personsRepository.save(person);
		log.debug("person:\n{}", personsRepository.loadInitializedById(person.getId()));
		assertThat(person.getCats()).extracting(Cat::getName).contains("cat1", "catX");
		assertThat(person.getFirstName()).isEqualTo("gigi");
		assertThat(person.getFriend().getFirstName()).isEqualTo("friend1-changed");
	}

	private Person createThenReloadPerson() {
		Person person = personsRepository.insert(generatePerson());
		return personsRepository.loadInitializedById(person.getId());
	}

	private Person generatePerson() {
		return new Person("gigi", "gigi",
				new Person("friend1", "friend1",
						null, List.of(new Cat("friend1-cat1"))),
				new ArrayList<>(List.of(new Cat("cat1"), new Cat("cat2"))));
	}
}
