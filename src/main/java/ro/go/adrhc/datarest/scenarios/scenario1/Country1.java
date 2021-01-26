package ro.go.adrhc.datarest.scenarios.scenario1;

import lombok.*;
import ro.go.adrhc.datarest.entities.BaseEntity;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Country1 extends BaseEntity {
	private String name;

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
		if (!(o instanceof Country1)) return false;
		Country1 other = (Country1) o;
		return id != null && id.equals(other.getId());
	}
}
