package ro.go.adrhc.datarest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ro.go.adrhc.datarest.dto.PersonDto;
import ro.go.adrhc.datarest.dto.Problem;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.person.PersonsRepository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ro.go.adrhc.datarest.util.HibernateUtils.initializeNestedProperties;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
@Slf4j
public class PersonsController {
	private final PersonsRepository repository;

	private static void failFor(Person person) {
		if (person.getFirstName().equalsIgnoreCase("error")) {
			throw new RuntimeException();
		}
	}

	@GetMapping(path = "{id}")
	public PersonDto findById(@PathVariable Integer id) {
		return repository.loadDtoById(id);
	}

	@GetMapping
	public Iterable<PersonDto> findAll() {
		return initializeNestedProperties(repository.findAllDto(), "cats");
	}

	@PostMapping
	public PersonDto create(@RequestBody Person person) {
		failFor(person);
		person = repository.insertOrUpdate(person);
		// only this way the cat.personId is correctly returned (after being set by repository)
		return repository.loadDtoById(person.getId());
	}

	@PutMapping(path = "{id}")
	public PersonDto update(@RequestBody Person person) {
		// issues with null cats (search for []):
		// https://stackoverflow.com/questions/5587482/hibernate-a-collection-with-cascade-all-delete-orphan-was-no-longer-referenc
		failFor(person);
		repository.save(person);
		// only this way the cat.personId is correctly returned (after being set by repository)
		return repository.loadDtoById(person.getId());
	}

	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable Integer id) {
		if (id > 10) {
			throw new RuntimeException();
		}
		repository.deleteById(id);
	}

	@ExceptionHandler({Exception.class})
	@ResponseStatus
	@ResponseBody
	public List<Problem<?>> reportProblem(Exception e) {
		var msg = e.getMessage();
		log.error(msg, e);
		return List.of(
				new Problem<>(msg == null ? "bad day1" : msg, ThreadLocalRandom.current().nextInt()),
				new Problem<>(msg == null ? "bad day2" : msg, ThreadLocalRandom.current().nextInt()),
				new Problem<>(msg == null ? "bad day3" : msg, ThreadLocalRandom.current().nextInt())
		);
	}
}
