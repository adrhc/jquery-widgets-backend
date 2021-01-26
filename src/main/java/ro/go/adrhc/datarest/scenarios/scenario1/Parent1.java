package ro.go.adrhc.datarest.scenarios.scenario1;

import lombok.*;
import ro.go.adrhc.datarest.entities.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Parent1 extends BaseEntity {
	private String name;
	@OneToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "birth_country_id")
	private Country1 birthPlace;
	@OneToOne
	@JoinColumn(name = "married_country_id")
	private Country1 marriedPlace;

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
		if (!(o instanceof Parent1)) return false;
		Parent1 other = (Parent1) o;
		return id != null && id.equals(other.getId());
	}
}
