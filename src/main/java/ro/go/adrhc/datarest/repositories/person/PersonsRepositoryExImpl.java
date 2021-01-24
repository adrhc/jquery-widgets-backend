package ro.go.adrhc.datarest.repositories.person;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.dto.CatDto;
import ro.go.adrhc.datarest.dto.PersonDto;
import ro.go.adrhc.datarest.entities.BaseEntity;
import ro.go.adrhc.datarest.entities.Cat;
import ro.go.adrhc.datarest.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ro.go.adrhc.datarest.util.HibernateUtils.initializeNestedProperties;

@RequiredArgsConstructor
@Transactional
public class PersonsRepositoryExImpl implements PersonsRepositoryEx {
	private final EntityManager em;

	private static <T> void initCollection(Collection<T> collection) {
		if (collection == null) {
			return;
		}
		collection.size();
	}

	public Person create(Person person) {
		person.setFriend(getFkEntity(Person.class, person.getFriend()));
		em.persist(person);
		if (person.getCats() == null) {
			return person;
		}
		return person;
	}

	private <T extends BaseEntity> T getFkEntity(Class<T> clazz, T fkEntity) {
		if (fkEntity == null || fkEntity.getId() == null) {
			return null;
		}
		return em.find(clazz, fkEntity.getId());
	}

	public PersonDto loadDtoById(Integer id) {
		TypedQuery<Person> query = em.createQuery("FROM Person p LEFT JOIN FETCH p.friend WHERE p.id = :id", Person.class);
		query.setParameter("id", id);
		Person person = query.getSingleResult();

		// detach and remove friend's references
		Person friend = detachPerson(person.getFriend());

		return new PersonDto(friend, catsDtoOf(person.getCats()), person);
	}

	private List<CatDto> catsDtoOf(List<Cat> cats) {
		if (cats == null) {
			return null;
		}
		return cats.stream()
				.map(cat -> {
					if (cat.getFriendId() != null) {
						Person catFriend = detachPerson(em.find(Person.class, cat.getFriendId()));
						return new CatDto(catFriend, cat);
					}
					return new CatDto(cat);
				})
				.collect(Collectors.toList());
	}

	private Person detachPerson(Person person) {
		if (person != null) {
			em.detach(person);
			person.setFriend(null);
			person.setCats(null);
		}
		return person;
	}

	@Override
	public List<Person> findAll() {
		TypedQuery<Person> query = em.createQuery("FROM Person p LEFT JOIN FETCH p.friend", Person.class);
		List<Person> persons = query.getResultList();
		return initializeNestedProperties(persons, "cats");
	}
}
