package ro.go.adrhc.datarest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.PersonsRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonsController {
	private final PersonsRepository repository;

	@GetMapping(path = "{id}")
	public Optional<Person> findById(@PathVariable Integer id) {
		return repository.findById(id);
	}

	@GetMapping
	public List<Person> findAll() {
		return repository.findAll();
	}

	@PutMapping
	@PostMapping
	public Person save(Person person) {
		return repository.save(person);
	}

	@DeleteMapping
	public void delete(Person person) {
		repository.delete(person);
	}
}
