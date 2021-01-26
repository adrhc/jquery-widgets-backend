package ro.go.adrhc.datarest.repositories.parent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.config.SpringJpaTest;
import ro.go.adrhc.datarest.entities.scenario1.Parent;
import ro.go.adrhc.datarest.repositories.CountryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringJpaTest
class ParentRepositoryExTest {
	@Autowired
	private ParentRepository parentRepository;
	@Autowired
	private CountryRepository countryRepository;

	@Transactional(propagation = Propagation.NEVER)
	@Test
	void generateParent() {
		Parent parent1 = parentRepository.generateParent();
		log.debug("\n{}", parent1.toString());
		parent1.getBirthPlace().setName(parent1.getBirthPlace().getName() + "-changed");
		parent1.getMarriedPlace().setName(parent1.getMarriedPlace() + "-changed");
		// birthPlace: CascadeType.PERSIST
		// marriedPlace: no cascade type set
		Parent parent2 = new Parent("parent2", null, parent1.getMarriedPlace(), null);
		parent2 = parentRepository.insert(parent2);
		log.debug("\n{}", parent2.toString());
		assertThat(parent2.getMarriedPlace().getName()).endsWith("-changed");
		// parent2.getMarriedPlace() is in fact not changed in DB!
		assertThat(countryRepository.findById(parent2.getMarriedPlace().getId()))
				.isPresent().hasValueSatisfying((c) -> assertThat(c.getName()).doesNotEndWith("-changed"));
		// ERROR: detached entity passed to persist
		Parent parent3 = new Parent("parent3", parent1.getBirthPlace(), parent1.getMarriedPlace(), null);
		assertThrows(InvalidDataAccessApiUsageException.class, () -> parentRepository.insert(parent3));
	}
}
