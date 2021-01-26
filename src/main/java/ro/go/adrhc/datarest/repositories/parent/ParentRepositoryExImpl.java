package ro.go.adrhc.datarest.repositories.parent;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.entities.scenario1.Country;
import ro.go.adrhc.datarest.entities.scenario1.Parent;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Transactional
public class ParentRepositoryExImpl implements ParentRepositoryEx {
	private final EntityManager em;

	public Parent generateParent() {
		Country marriedPlace = new Country("country2");
		em.persist(marriedPlace);
		Parent parent = new Parent("parent1", new Country("country1"), marriedPlace);
//				marriedPlace, Set.of(new Child("child1", null)));
		em.persist(parent);
		return parent;
	}

	public Parent insert(Parent parent) {
		em.persist(parent);
		return parent;
	}

	public Parent update(Parent parent) {
		return em.merge(parent);
	}
}
