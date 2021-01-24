package ro.go.adrhc.datarest.repositories.person;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static ro.go.adrhc.datarest.util.HibernateUtils.initializeNestedProperties;

@RequiredArgsConstructor
@Transactional
public class PersonsRepositoryExImpl implements PersonsRepositoryEx {
	private final EntityManager em;

	public Person loadById(Integer id) {
		TypedQuery<Person> query = em.createQuery("FROM Person p LEFT JOIN FETCH p.friend WHERE p.id = :id", Person.class);
		query.setParameter("id", id);
		Person person = query.getSingleResult();
		person.getCats().size();
		return person;
	}

	@Override
	public List<Person> findAll() {
		TypedQuery<Person> query = em.createQuery("FROM Person p LEFT JOIN FETCH p.friend", Person.class);
		List<Person> persons = query.getResultList();
		return initializeNestedProperties(persons, "cats");
	}
}
