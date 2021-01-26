package ro.go.adrhc.datarest.entities.scenario1;

import lombok.*;
import ro.go.adrhc.datarest.entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Parent extends BaseEntity {
	private String name;
	@OneToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "birth_country_id")
	private Country birthPlace;
	@OneToOne
	@JoinColumn(name = "married_country_id")
	private Country marriedPlace;
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "parent_id")
	private Set<Child> children;

	/**
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	 */
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Parent)) return false;
		Parent other = (Parent) o;
		return id != null && id.equals(other.getId());
	}
}
