package ro.go.adrhc.datarest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Cat {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Cat)) return false;
		Cat other = (Cat) o;
		return id != null && id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
