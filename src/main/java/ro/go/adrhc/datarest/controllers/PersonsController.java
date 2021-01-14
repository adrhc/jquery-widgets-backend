package ro.go.adrhc.datarest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.PersonsRepository;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/persons")
public class PersonsController {
	private final PersonsRepository repository;

	@GetMapping(path = "{id}")
	public Optional<Person> findById(@PathVariable Integer id) {
		return repository.findById(id);
	}

	@GetMapping
	public Iterable<Person> findAll() {
		return repository.findAll();
	}

	@PostMapping
	public Person create(@RequestBody Person person) {
		return repository.save(person);
	}

	@PutMapping(path = "{id}")
	public Person update(@RequestBody Person person) {
		// https://stackoverflow.com/questions/5587482/hibernate-a-collection-with-cascade-all-delete-orphan-was-no-longer-referenc
		if (person.getCats() == null) {
			person.setCats(Collections.emptyList());
		}
		return repository.save(person);
	}

	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable Integer id) {
		repository.deleteById(id);
	}
}
