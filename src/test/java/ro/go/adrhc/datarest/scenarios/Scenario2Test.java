package ro.go.adrhc.datarest.scenarios;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.go.adrhc.datarest.config.SpringJpaTest;
import ro.go.adrhc.datarest.scenarios.scenario2.ParentRepository;

@Slf4j
@SpringJpaTest
public class Scenario2Test {
	@Autowired
	private ParentRepository parentRepository;

	@Transactional(propagation = Propagation.NEVER)
	@Test
	void test() {

	}
}
