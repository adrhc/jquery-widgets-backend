package ro.go.adrhc.datarest.repositories.person;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.dto.CatDto;
import ro.go.adrhc.datarest.dto.PersonDto;
import ro.go.adrhc.datarest.entities.BaseEntity;
import ro.go.adrhc.datarest.entities.Cat;
import ro.go.adrhc.datarest.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class PersonsRepositoryExImpl implements PersonsRepositoryEx {
	private final EntityManager em;

	public Person insert(Person person) {
		em.persist(person);
		return person;
	}

	public Person merge(Person person) {
		return em.merge(person);
	}

	private <T extends BaseEntity> T getFkEntity(Class<T> clazz, T fkEntity) {
		if (fkEntity == null || fkEntity.getId() == null) {
			return fkEntity;
		}
		return em.find(clazz, fkEntity.getId());
	}

	public Person loadInitializedById(Integer id) {
		Person person = em.find(Person.class, id);
		this.initializePerson(person);
		return person;
	}

	private void initializePerson(Person person) {
		Hibernate.initialize(person.getCats());
		if (person.getFriend() != null) {
			initializePerson(person.getFriend());
		}
	}

	public PersonDto loadDtoById(Integer id) {
		TypedQuery<Person> query = em.createQuery("FROM Person p LEFT JOIN FETCH p.friend WHERE p.id = :id", Person.class);
		query.setParameter("id", id);
		return personDtoOf(query.getSingleResult());
	}

	private PersonDto personDtoOf(Person person) {
		if (person == null) {
			return null;
		}
		// detach and remove friend's references (friend & cats)
		// person.getFriend() might be detached by catsDtoOf
		Person friend = detachPerson(person.getFriend());
		// get the cats before clearing them on person detach
		// this detaches persons and might detach above person's friend too
		List<CatDto> catsDto = catsDtoOf(person.getCats());
		// detach and remove person's references (friend & cats)
		person = detachPerson(person);
		return new PersonDto(person, friend, catsDto);
	}

	private List<CatDto> catsDtoOf(List<Cat> cats) {
		if (cats == null) {
			return null;
		}
		return cats.stream()
				.map(cat -> {
					if (cat.getFriendId() == null) {
						return new CatDto(cat);
					}
					Person catFriend = detachPersonById(cat.getFriendId());
					return new CatDto(catFriend, cat);
				})
				.collect(Collectors.toList());
	}

	private Person detachPersonById(Integer personId) {
		return detachPerson(em.find(Person.class, personId));
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
	public List<PersonDto> findAllDto() {
		TypedQuery<Person> query = em.createQuery("FROM Person p LEFT JOIN FETCH p.friend", Person.class);
		List<Person> persons = query.getResultList();
		return persons.stream().map(this::personDtoOf).collect(Collectors.toList());
	}
}
