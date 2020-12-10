package ro.go.adrhc.datarest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Person implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String lastName;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("name")
	private List<Cat> cats;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Person)) return false;
		Person other = (Person) o;
		return id != null && id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
