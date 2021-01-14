package ro.go.adrhc.datarest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.go.adrhc.datarest.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * spring 3.2.6 style (though this should use HibernateTemplate)
 */
@Repository
public class DynaSelOneRepository {
	private static Map<String, EntityDynaSelOneDetails> details = Map.of(
			"person", new EntityDynaSelOneDetails("Person", "firstName", Person.class));

	@Autowired
	private EntityManager em;

	public <T> List<T> findByTitle(String title, String entityName) {
		EntityDynaSelOneDetails<T> details = DynaSelOneRepository.details.get(entityName);
//		return this.hibernateTemplate().find("FROM " + details.getEntityName() +
//				" WHERE " + details.getTitleField() + " LIKE ?0 || '%'", title);
		TypedQuery<T> query = em.createQuery("FROM " + details.getEntityName() +
				" WHERE " + details.getTitleField() + " LIKE :firstName || '%'", details.getEntityClass());
		query.setParameter("firstName", title);
		return query.getResultList();
	}
}
