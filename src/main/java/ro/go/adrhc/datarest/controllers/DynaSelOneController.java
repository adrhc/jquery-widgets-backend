package ro.go.adrhc.datarest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.go.adrhc.datarest.repositories.dynaselone.DynaSelOneRepository;

import java.util.List;

@RestController("/dynaselone")
@RequiredArgsConstructor
public class DynaSelOneController {
	private final DynaSelOneRepository dynaSelOneRepository;

	@GetMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List findByTitle(@RequestParam String title, @RequestParam String entity) {
		return dynaSelOneRepository.findByTitle(title, entity);
	}
}
