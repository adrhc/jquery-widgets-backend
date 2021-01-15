package ro.go.adrhc.datarest.repositories.dynaselone;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static ro.go.adrhc.datarest.util.HibernateUtils.initializeNestedProperties;

/**
 * spring 3.2.6 working style (though this should use HibernateTemplate)
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class DynaSelOneRepository {
	private final ApplicationContext ac;
	private final EntityManager em;

	public <T> List<T> findByTitle(String title, String entityName) {
		DynaSelOneDetails<T> details = dynaSelOneDetailsOf(entityName);
//		return this.hibernateTemplate().find("FROM " + details.getEntityName() +
//				" WHERE " + details.getTitleField() + " LIKE ?0 || '%'", title);
		TypedQuery<T> query = em.createQuery("FROM " + details.getEntityName() +
				" WHERE " + details.getTitleField() + " LIKE :firstName || '%'", details.getEntityClass());
		query.setParameter("firstName", title);
		List<T> entities = query.getResultList();
		return initializeNestedProperties(entities, details.getNestedProperties());
	}

	private <T> DynaSelOneDetails<T> dynaSelOneDetailsOf(String entityName) {
		return (DynaSelOneDetails<T>) ac.getBean(entityName.concat("DynaSelOneDetails"));
	}
}
