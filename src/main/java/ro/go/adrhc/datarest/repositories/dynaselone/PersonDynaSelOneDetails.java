package ro.go.adrhc.datarest.repositories.dynaselone;

import org.springframework.stereotype.Component;
import ro.go.adrhc.datarest.entities.Person;

@Component
public class PersonDynaSelOneDetails extends DynaSelOneDetails<Person> {
	public PersonDynaSelOneDetails() {
		super("Person", "firstName", Person.class, "cats", "friend.cats");
	}
}
