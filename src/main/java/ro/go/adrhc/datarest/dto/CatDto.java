package ro.go.adrhc.datarest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.go.adrhc.datarest.entities.Cat;
import ro.go.adrhc.datarest.entities.Person;

@Getter
@Setter
@NoArgsConstructor
public class CatDto {
	private Integer id;
	private String name;
	private Integer friendId;
	private Person person;

	public CatDto(Cat cat) {
		this.id = cat.getId();
		this.name = cat.getName();
		this.friendId = cat.getFriendId();
	}

	public CatDto(Person friend, Cat cat) {
		this(cat);
		this.person = friend;
	}
}
