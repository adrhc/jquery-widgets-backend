package ro.go.adrhc.datarest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

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
		});
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		//		config.setAllowedMethods(EnumSet.allOf(RequestMethod.class)
		//				.stream().map(Enum::name).collect(Collectors.toList()));
		config.addAllowedMethod(ALL);
		config.addAllowedOrigin(ALL);
		config.addAllowedHeader(ALL);
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
