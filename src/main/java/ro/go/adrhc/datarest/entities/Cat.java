package ro.go.adrhc.datarest.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cat extends BaseEntity {
	private String name;
	@Column(name = "friend_id", insertable = false, updatable = false)
	private Integer friendId;
//	@ManyToOne
//	private Person person;

	public Cat(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cat cat = (Cat) o;

		return id != null ? id.equals(cat.id) : cat.id == null;
	}

	/**
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	 */
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
