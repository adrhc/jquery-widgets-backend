package ro.go.adrhc.datarest.repositories.dynaselone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * spring 3.2.6 style (though this should use HibernateTemplate)
 */
@Transactional
@Repository
public class DynaSelOneRepository {
	private static Map<String, EntityDynaSelOneDetails> details = Map.of(
			"person", new EntityDynaSelOneDetails<>("Person", "firstName", Person.class,
					(persons) -> {
						// initialize cats collection
						persons.forEach(it -> it.getCats().size());
						return persons;
					}));

	@Autowired
	private EntityManager em;

	public <T, R> List<R> findByTitle(String title, String entityName) {
		EntityDynaSelOneDetails<T, R> details = DynaSelOneRepository.details.get(entityName);
//		return this.hibernateTemplate().find("FROM " + details.getEntityName() +
//				" WHERE " + details.getTitleField() + " LIKE ?0 || '%'", title);
		TypedQuery<T> query = em.createQuery("FROM " + details.getEntityName() +
				" WHERE " + details.getTitleField() + " LIKE :firstName || '%'", details.getEntityClass());
		query.setParameter("firstName", title);
		return details.getTransformer().apply(query.getResultList());
	}
}
