package ro.go.adrhc.datarest.scenarios;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.config.SpringJpaTest;
import ro.go.adrhc.datarest.scenarios.scenario1.Country;
import ro.go.adrhc.datarest.scenarios.scenario1.CountryRepository;
import ro.go.adrhc.datarest.scenarios.scenario1.Parent;
import ro.go.adrhc.datarest.scenarios.scenario1.ParentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringJpaTest
class Scenario1Test {
	@Autowired
	private ParentRepository parentRepository;
	@Autowired
	private CountryRepository countryRepository;

	@Transactional(propagation = Propagation.NEVER)
	@Test
	void generateParent() {
		// birthPlace: CascadeType.PERSIST
		// marriedPlace: no cascade type set
		Parent parent1 = parentRepository.generateParent();
		log.debug("inserted parent1:\n{}", parent1.toString());

		// parent insert: detached marriedPlace not changed in DB
		parent1.getBirthPlace().setName(parent1.getBirthPlace().getName() + "-changed");
		parent1.getMarriedPlace().setName(parent1.getMarriedPlace() + "-changed");
		Parent parent2 = new Parent("parent2", null, parent1.getMarriedPlace());
		parent2 = parentRepository.insert(parent2);
		log.debug("inserted parent2:\n{}", parent2.toString());
		assertThat(parent2.getMarriedPlace().getName()).endsWith("-changed");
		// parent2.getMarriedPlace() is in fact not changed in DB!
		Optional<Country> marriedPlaceParent1aOpt = countryRepository.findById(parent2.getMarriedPlace().getId());
		assertThat(marriedPlaceParent1aOpt).isPresent()
				.hasValueSatisfying((c) -> assertThat(c.getName()).doesNotEndWith("-changed"));

		// parent insert: detached birthPlace not accepted
		Parent parent3 = new Parent("parent3", parent1.getBirthPlace(), parent1.getMarriedPlace());
		// ERROR: detached entity (parent1.getBirthPlace()) passed to persist
		assertThrows(InvalidDataAccessApiUsageException.class, () -> parentRepository.insert(parent3));

		Country marriedPlaceParent1a = marriedPlaceParent1aOpt.get();
		Parent parent4 = new Parent("parent4", new Country("country4"), marriedPlaceParent1a);
		parent4 = parentRepository.insert(parent4);

		// parent update: detached birthPlace not changed in DB
		parent4.setName(parent4.getName() + "-changed1");
		parent4.getBirthPlace().setName(parent4.getBirthPlace().getName() + "-changed");
		parent4 = parentRepository.update(parent4);
		log.debug("updated parent4:\n{}", parent4.toString());
		assertThat(parent4.getName()).endsWith("-changed1");
		Optional<Country> birthPlace4 = countryRepository.findById(parent4.getBirthPlace().getId());
		assertThat(birthPlace4).isPresent()
				.hasValueSatisfying((c) -> assertThat(c.getName()).doesNotEndWith("-changed"));

		// parent update: detached marriedPlace not changed in DB
		parent4.setName(parent4.getName() + "-changed2");
		parent4.getMarriedPlace().setName(parent4.getMarriedPlace().getName() + "-changed2");
		parent4 = parentRepository.update(parent4);
		log.debug("updated parent4:\n{}", parent4.toString());
		assertThat(parent4.getName()).endsWith("-changed2");
		Optional<Country> marriedPlaceParent1bOpt = countryRepository.findById(parent4.getMarriedPlace().getId());
		assertThat(marriedPlaceParent1bOpt).isPresent()
				.hasValueSatisfying((c) -> assertThat(c.getName()).doesNotEndWith("-changed"));
	}
}
