package ro.go.adrhc.datarest.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {
	private String firstName;
	private String lastName;
//	@OneToOne(cascade = {CascadeType.PERSIST})
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "friend_id")
	private Person friend;
	/**
	 * initialization with new ArrayList() solves:
	 * JpaSystemException: A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: ro.go.adrhc.datarest.entities.Person.cats
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("name")
	@JoinColumn(name = "owner_id")
	private List<Cat> cats = new ArrayList<>();
//	private List<Cat> cats;

	public Person(Integer id, String firstName, String lastName, Person friend, List<Cat> cats) {
		this(firstName, lastName, friend, cats);
		this.id = id;
	}

	public Person(Integer id, String firstName, String lastName) {
		this(firstName, lastName);
		this.id = id;
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Person(Integer id) {
		super(id);
	}

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
