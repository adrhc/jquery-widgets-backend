package ro.go.adrhc.datarest.scenarios.scenario2;

import lombok.*;
import ro.go.adrhc.datarest.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Child extends BaseEntity {
	private String name;
	@Column(name = "parent_id", updatable = false, insertable = false)
	private String parentId;

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
		if (!(o instanceof Child)) return false;
		Child other = (Child) o;
		return id != null && id.equals(other.getId());
	}
}
