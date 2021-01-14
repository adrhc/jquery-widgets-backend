package ro.go.adrhc.datarest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.go.adrhc.datarest.entities.Person;
import ro.go.adrhc.datarest.repositories.dynaselone.DynaSelOneRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DynaSelOneController.class)
class DynaSelOneControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private DynaSelOneRepository repository;

	@Test
	void findByTitle() throws Exception {
		List<Object> persons = List.of(new Person(1, "gigi", "gigi", null));
		given(repository.findByTitle(anyString(), eq("person"))).willReturn(persons);
		this.mvc.perform(get("/dynaselone")
				.queryParam("title", "gigi")
				.queryParam("entity", "person")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].firstName").value("gigi"));
	}
}
