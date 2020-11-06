package ro.go.adrhc.datarest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;
import java.util.EnumSet;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@SpringBootApplication
public class DataRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataRestApplication.class, args);
	}

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer(EntityManager entityManager) {
		return RepositoryRestConfigurer.withConfig(config -> {
			config.exposeIdsFor(entityManager.getMetamodel().getEntities()
					.stream().map(Type::getJavaType).toArray(Class[]::new));
			config.getCorsRegistry()
					.addMapping("/**")
					.allowedOrigins(ALL)
					.allowedMethods(EnumSet.allOf(RequestMethod.class).stream().map(Enum::name).toArray(String[]::new))
					.allowedHeaders(ALL)
					.allowCredentials(true);
		});
	}
}
