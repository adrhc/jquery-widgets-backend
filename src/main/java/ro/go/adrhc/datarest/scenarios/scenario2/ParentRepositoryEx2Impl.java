package ro.go.adrhc.datarest.scenarios.scenario2;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

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

	public Optional<Parent2> getById(Integer id) {
		Parent2 parent = em.find(Parent2.class, id);
		if (parent != null) {
			Hibernate.initialize(parent.getChildren());
		}
		return Optional.ofNullable(parent);
	}
}
