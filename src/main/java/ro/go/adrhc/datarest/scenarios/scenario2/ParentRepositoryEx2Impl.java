package ro.go.adrhc.datarest.scenarios.scenario2;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Transactional
public class ParentRepositoryEx2Impl implements ParentRepositoryEx2 {
	private final EntityManager em;

	public Parent2 insert(Parent2 parent) {
		em.persist(parent);
		return parent;
	}

	public Parent2 update(Parent2 parent) {
		return em.merge(parent);
	}
}
