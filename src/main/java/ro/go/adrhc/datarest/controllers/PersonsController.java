package ro.go.adrhc.datarest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.PersonsRepository;

import java.util.Optional;

import static ro.go.adrhc.datarest.util.HibernateUtils.initializeNestedProperties;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonsController {
	private final PersonsRepository repository;

	private static void clearFakeIds(Person person) {
		person.getCats().forEach(c -> c.setId(c.getId() < 0 ? null : c.getId()));
	}

	@GetMapping(path = "{id}")
	public Optional<Person> findById(@PathVariable Integer id) {
		return repository.findById(id);
	}

	@GetMapping
	public Iterable<Person> findAll() {
		return initializeNestedProperties(repository.findAll(), "cats");
	}

	@PostMapping
	public Person create(@RequestBody Person person) {
		clearFakeIds(person);
		return repository.save(person);
	}

	@PutMapping(path = "{id}")
	public Person update(@RequestBody Person person) {
		// issues with null cats (search for []):
		// https://stackoverflow.com/questions/5587482/hibernate-a-collection-with-cascade-all-delete-orphan-was-no-longer-referenc
		clearFakeIds(person);
		return repository.save(person);
	}

	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable Integer id) {
		repository.deleteById(id);
	}
}
