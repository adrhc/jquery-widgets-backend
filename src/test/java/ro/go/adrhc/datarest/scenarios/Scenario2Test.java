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
import java.util.Set;

@Slf4j
@SpringJpaTest
public class Scenario2Test {
	@Autowired
	private ParentRepository2 parentRepository2;

	@Transactional(propagation = Propagation.NEVER)
	@Test
	void test() {
		Parent2 parent1 = new Parent2("parent1", new HashSet<>(Set.of(new Child2("child1"))));
		parent1 = parentRepository2.insert(parent1);
		log.debug("inserted parent1:\n{}", parent1.toString());
		parent1.getChildren().add(new Child2("child2"));
		parent1 = parentRepository2.update(parent1);
		log.debug("updated parent1:\n{}", parent1.toString());
	}
}
