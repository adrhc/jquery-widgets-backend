package ro.go.adrhc.datarest.scenarios.scenario2;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Transactional
public class ParentRepositoryExImpl implements ParentRepositoryEx {
	private final EntityManager em;

	public Parent insert(Parent parent) {
		em.persist(parent);
		return parent;
	}

	public Parent update(Parent parent) {
		return em.merge(parent);
	}
}
