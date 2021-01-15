package ro.go.adrhc.datarest.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cat implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	@Column(name = "person_id", insertable = true, updatable = false)
	private Integer personId;
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
