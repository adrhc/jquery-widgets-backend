package ro.go.adrhc.datarest;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@Log
class DataRestApplicationTests {
	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		log.fine(String.join("\n", context.getBeanDefinitionNames()));
	}
}
