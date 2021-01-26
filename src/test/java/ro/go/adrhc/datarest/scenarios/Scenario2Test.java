package ro.go.adrhc.datarest.scenarios;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.config.SpringJpaTest;
import ro.go.adrhc.datarest.scenarios.scenario2.Child2;
import ro.go.adrhc.datarest.scenarios.scenario2.Parent2;
import ro.go.adrhc.datarest.scenarios.scenario2.ParentRepository2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringJpaTest
public class Scenario2Test {
	@Autowired
	private ParentRepository2 parentRepository2;

	@Transactional(propagation = Propagation.NEVER)
	@Test
	void test() {
		// creating parent with 1 child
		Parent2 parent1 = new Parent2("parent1", new HashSet<>(Set.of(new Child2("child1"))));
		parent1 = parentRepository2.insert(parent1);
		log.debug("inserted parent1:\n{}", parent1.toString());
		Optional<Parent2> parent1Opt = parentRepository2.getById(parent1.getId());
		assertThat(parent1Opt).isPresent().hasValueSatisfying(p2 -> assertThat(p2.getChildren()).hasSize(1));
		log.debug("1. from DB parent1:\n{}", parent1Opt.get());

		// updating parent by adding 1 child
		parent1.getChildren().add(new Child2("child2"));
		parent1 = parentRepository2.update(parent1);
		log.debug("updated parent1:\n{}", parent1.toString());
		parent1Opt = parentRepository2.getById(parent1.getId());
		assertThat(parent1Opt).isPresent().hasValueSatisfying(p2 -> assertThat(p2.getChildren()).hasSize(2));
		log.debug("2. from DB parent1:\n{}", parent1Opt.get());

		// updating parent by adding 1 child and removing the 1th existing child
		parent1.getChildren().remove(parent1.getChildren().iterator().next());
		parent1.getChildren().add(new Child2("child3"));
		parent1 = parentRepository2.update(parent1);
		log.debug("updated parent1:\n{}", parent1.toString());
		parent1Opt = parentRepository2.getById(parent1.getId());
		assertThat(parent1Opt).isPresent().hasValueSatisfying(p2 -> assertThat(p2.getChildren()).hasSize(2));
		log.debug("2. from DB parent1:\n{}", parent1Opt.get());
	}
}
