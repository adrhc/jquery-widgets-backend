package ro.go.adrhc.datarest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.EnumSet;
import java.util.stream.Collectors;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@SpringBootApplication
public class DataRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataRestApplication.class, args);
	}

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
				registry.addMapping("/persons").allowedOrigins(ALL);
				registry.addMapping("/dynaselone").allowedOrigins(ALL);
			}
		};
	}
*/
}
