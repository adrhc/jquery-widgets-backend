package ro.go.adrhc.datarest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.go.adrhc.datarest.dto.PersonDto;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.PersonsRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonsController.class)
class PersonsControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private PersonsRepository repository;

	@Test
	void findById() throws Exception {
		PersonDto personDto = new PersonDto(new Person(1, "gigi", "gigi"), null, null);
		given(repository.loadDtoById(1)).willReturn(personDto);
		this.mvc.perform(get("/persons/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("gigi"));
	}

	@Test
	void findAll() throws Exception {
		Person person = new Person(1, "gigi", "gigi", null, null);
		given(repository.findAll()).willReturn(List.of(person));
		this.mvc.perform(get("/persons")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray());
	}
}
