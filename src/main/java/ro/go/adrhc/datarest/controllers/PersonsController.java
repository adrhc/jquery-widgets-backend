package ro.go.adrhc.datarest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.PersonsRepository;

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

	@PutMapping
	public Person update(@RequestBody Person person) {
		return repository.save(person);
	}

	@PostMapping
	public Person create(@RequestBody Person person) {
		return repository.save(person);
	}

	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable Integer id) {
		repository.deleteById(id);
	}
}
