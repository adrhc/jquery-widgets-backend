package ro.go.adrhc.datarest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataRestApplication.class, args);
	}

	/**
	 * https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.query-methods.at-query.custom-rowmapper
	 */
/*
	@Bean
	QueryMappingConfiguration rowMappers() {
		return new DefaultQueryMappingConfiguration()
				.registerRowMapper(Cat.class, (ResultSet rs, int rowNum) -> {
					rs.getMetaData().getColumnName(rowNum)
				});
	}
*/

/*
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
*/

	/**
	 * https://spring.io/guides/gs/rest-service-cors/#global-cors-configuration
	 */
/*
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins(ALL);
				registry.addMapping("/persons").allowedOrigins(ALL);
				registry.addMapping("/dynaselone").allowedOrigins(ALL);
			}
		};
	}
*/
}
