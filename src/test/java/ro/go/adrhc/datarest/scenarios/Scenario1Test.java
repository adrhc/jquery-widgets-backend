package ro.go.adrhc.datarest.scenarios;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.config.SpringJpaTest;
import ro.go.adrhc.datarest.scenarios.scenario1.Country1;
import ro.go.adrhc.datarest.scenarios.scenario1.Country1Repository;
import ro.go.adrhc.datarest.scenarios.scenario1.Parent1;
import ro.go.adrhc.datarest.scenarios.scenario1.Parent1Repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringJpaTest
class Scenario1Test {
	@Autowired
	private Parent1Repository parent1Repository;
	@Autowired
	private Country1Repository country1Repository;

	@Transactional(propagation = Propagation.NEVER)
	@Test
	void test() {
		// birthPlace: CascadeType.PERSIST
		// marriedPlace: no cascade type set
		Parent1 parent1 = parent1Repository.generateParent();
		log.debug("inserted parent1:\n{}", parent1.toString());

		// parent insert: detached marriedPlace not changed in DB
		parent1.getBirthPlace().setName(parent1.getBirthPlace().getName() + "-changed");
		parent1.getMarriedPlace().setName(parent1.getMarriedPlace() + "-changed");
		Parent1 parent2 = new Parent1("parent2", null, parent1.getMarriedPlace());
		parent2 = parent1Repository.insert(parent2);
		log.debug("inserted parent2:\n{}", parent2.toString());
		assertThat(parent2.getMarriedPlace().getName()).endsWith("-changed");
		// parent2.getMarriedPlace() is in fact not changed in DB!
		Optional<Country1> marriedPlaceParent1aOpt = country1Repository.findById(parent2.getMarriedPlace().getId());
		assertThat(marriedPlaceParent1aOpt).isPresent()
				.hasValueSatisfying((c) -> assertThat(c.getName()).doesNotEndWith("-changed"));

		// parent insert: detached birthPlace not accepted
		Parent1 parent3 = new Parent1("parent3", parent1.getBirthPlace(), parent1.getMarriedPlace());
		// ERROR: detached entity (parent1.getBirthPlace()) passed to persist
		assertThrows(InvalidDataAccessApiUsageException.class, () -> parent1Repository.insert(parent3));

		Country1 marriedPlaceParent1a = marriedPlaceParent1aOpt.get();
		Parent1 parent4 = new Parent1("parent4", new Country1("country4"), marriedPlaceParent1a);
		parent4 = parent1Repository.insert(parent4);

		// parent update: detached birthPlace not changed in DB
		parent4.setName(parent4.getName() + "-changed1");
		parent4.getBirthPlace().setName(parent4.getBirthPlace().getName() + "-changed");
		parent4 = parent1Repository.update(parent4);
		log.debug("updated parent4:\n{}", parent4.toString());
		assertThat(parent4.getName()).endsWith("-changed1");
		Optional<Country1> birthPlace4 = country1Repository.findById(parent4.getBirthPlace().getId());
		assertThat(birthPlace4).isPresent()
				.hasValueSatisfying((c) -> assertThat(c.getName()).doesNotEndWith("-changed"));

		// parent update: detached marriedPlace not changed in DB
		parent4.setName(parent4.getName() + "-changed2");
		parent4.getMarriedPlace().setName(parent4.getMarriedPlace().getName() + "-changed2");
		parent4 = parent1Repository.update(parent4);
		log.debug("updated parent4:\n{}", parent4.toString());
		assertThat(parent4.getName()).endsWith("-changed2");
		Optional<Country1> marriedPlaceParent1bOpt = country1Repository.findById(parent4.getMarriedPlace().getId());
		assertThat(marriedPlaceParent1bOpt).isPresent()
				.hasValueSatisfying((c) -> assertThat(c.getName()).doesNotEndWith("-changed"));
	}
}
