package ro.go.adrhc.datarest.scenarios.scenario2;

import lombok.*;
import ro.go.adrhc.datarest.entities.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString(callSuper = true)
public class Parent2 extends BaseEntity {
	private String name;
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "parent_id")
	private Set<Child2> children;

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
		if (!(o instanceof Parent2)) return false;
		Parent2 other = (Parent2) o;
		return id != null && id.equals(other.getId());
	}
}
