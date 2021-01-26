package ro.go.adrhc.datarest.scenarios.scenario1;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Transactional
public class Parent1RepositoryExImpl implements Parent1RepositoryEx {
	private final EntityManager em;

	public Parent1 generateParent() {
		Country1 marriedPlace = new Country1("country2");
		em.persist(marriedPlace);
		Parent1 parent = new Parent1("parent1", new Country1("country1"), marriedPlace);
//				marriedPlace, Set.of(new Child("child1", null)));
		em.persist(parent);
		return parent;
	}

	public Parent1 insert(Parent1 parent) {
		em.persist(parent);
		return parent;
	}

	public Parent1 update(Parent1 parent) {
		return em.merge(parent);
	}
}
