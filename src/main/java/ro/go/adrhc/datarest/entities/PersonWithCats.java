package ro.go.adrhc.datarest.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "PersonWithCats", types = Person.class)
public interface PersonWithCats {
	String getId();

	String getFirstName();

	String getLastName();

	List<Cat> getCats();
}
