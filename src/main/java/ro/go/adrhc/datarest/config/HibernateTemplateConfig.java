package ro.go.adrhc.datarest.config;

import org.springframework.context.annotation.Configuration;

/**
 * https://github.com/linux-china/spring-boot-starter-hibernate5/blob/master/src/main/java/org/mvnsearch/spring/boot/hibernate/Hibernate5AutoConfiguration.java
 * see also JpaRepositoriesAutoConfiguration, HibernateJpaAutoConfiguration,
 * HibernateJpaConfiguration, HibernateJpaVendorAdapter, JpaTransactionManager,
 * EntityManagerFactory.unwrap
 */
@Configuration
public class HibernateTemplateConfig {
/*
	@Value("${spring.jpa.properties.hibernate.format_sql}")
	private String format_sql;

	hibernate5SessionFactory @Bean conflicts with spring-boot-starter-data-rest!!!
	
	@Bean
	public SessionFactory hibernate5SessionFactory(DataSource dataSource) {
		return new LocalSessionFactoryBuilder(dataSource)
				.addPackage("ro.go.adrhc.datarest.entities")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
				.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.format_sql", format_sql)
				.buildSessionFactory();
	}
*/

/*
	@Bean
	public HibernateTemplate hibernateTemplate(EntityManagerFactory entityManagerFactory) {
		return new HibernateTemplate(entityManagerFactory.unwrap(SessionFactory.class));
	}
*/

/*
	doesn't work

	@Bean
	public HibernateTransactionManager hibernate5TransactionManager(EntityManagerFactory entityManagerFactory) {
		return new HibernateTransactionManager(entityManagerFactory.unwrap(SessionFactory.class));
	}
*/
}
