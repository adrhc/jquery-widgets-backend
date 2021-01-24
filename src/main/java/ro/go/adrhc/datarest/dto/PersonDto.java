package ro.go.adrhc.datarest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.go.adrhc.datarest.entities.Person;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {
	private Integer id;
	private String firstName;
	private String lastName;
	private Person friend;
	private List<CatDto> cats;

	public PersonDto(Person friend, List<CatDto> cats, Person person) {
		this.id = person.getId();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.friend = friend;
		this.cats = cats;
	}
}
