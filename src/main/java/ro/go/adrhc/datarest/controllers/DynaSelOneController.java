package ro.go.adrhc.datarest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.go.adrhc.datarest.repositories.dynaselone.DynaSelOneRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/dynaselone")
@RequiredArgsConstructor
public class DynaSelOneController {
	private final DynaSelOneRepository dynaSelOneRepository;

	/**
	 * struts 1.x working style
	 */
	@GetMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List findByTitle(@RequestParam String title, @RequestParam String entity) {
		return dynaSelOneRepository.findByTitle(title, entity);
	}
}
