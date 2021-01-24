package ro.go.adrhc.datarest.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String lastName;
	@OneToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "friend_id")
	private Person friend;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("name")
	@JoinColumn(name = "person_id")
	private List<Cat> cats;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Person)) return false;
		Person other = (Person) o;
		return id != null && id.equals(other.getId());
	}

	/**
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	 */
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
