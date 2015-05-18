package br.ufba.dcc.mestrado.computacao.repository;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {	
	"classpath:persistence.properties"
})
public class CoreRepositoryConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		String username = env.getProperty("javax.persistence.jdbc.user");
        String password = env.getProperty("javax.persistence.jdbc.password");
        
        String url = env.getProperty("javax.persistence.jdbc.url");
		
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(env.getProperty("javax.persistence.jdbc.driver"));
		
		return dataSource; 
	}
	
	@Bean(destroyMethod="destroy")
	public LocalContainerEntityManagerFactoryBean  entityManagerFactoryBean() {
		
		Properties connectionProperties = new Properties();
		
		connectionProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		connectionProperties.put("hibernate.cache.provider_class", env.getProperty("hibernate.cache.provider_class"));
		connectionProperties.put("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
		
		connectionProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		connectionProperties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		connectionProperties.put("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
		connectionProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		
		//Hibernate Search
		
		if (env.containsProperty("hibernate.search.default.indexBase")) {
			connectionProperties.put("hibernate.search.default.indexBase", env.getProperty("hibernate.search.default.indexBase"));
		}
		
		if (env.containsProperty("hibernate.search.default.directory_provider"))
			connectionProperties.put("hibernate.search.default.directory_provider", env.getProperty("hibernate.search.default.directory_provider"));
		
		if (env.containsProperty("hibernate.search.default.indexBase"))
			connectionProperties.put("hibernate.search.default.indexBase", env.getProperty("hibernate.search.default.indexBase"));
		
		if (env.containsProperty("hibernate.search.default.optimizer.operation_limit.max"))
			connectionProperties.put("hibernate.search.default.optimizer.operation_limit.max", env.getProperty("hibernate.search.default.optimizer.operation_limit.max"));
		
		if (env.containsProperty("hibernate.search.default.optimizer.transaction_limit.max"))
			connectionProperties.put("hibernate.search.default.optimizer.transaction_limit.max", env.getProperty("hibernate.search.default.optimizer.transaction_limit.max"));
		
		if (env.containsProperty("hibernate.search.lucene_version"))
			connectionProperties.put("hibernate.search.lucene_version", env.getProperty("hibernate.search.lucene_version"));
		
		if  (env.containsProperty("hibernate.search.analyzer"))
			connectionProperties.put("hibernate.search.analyzer", env.getProperty("hibernate.search.analyzer"));
		
		if (env.containsProperty("hibernate.search.default.exclusive_index_use"))
			connectionProperties.put("hibernate.search.default.exclusive_index_use", env.getProperty("hibernate.search.default.exclusive_index_use"));
		
		LocalContainerEntityManagerFactoryBean  entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setPersistenceUnitName(env.getProperty("persistenceUnitName"));
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaProperties(connectionProperties);
		
		return entityManagerFactoryBean;
	}
	
	/**
	 * @see http://www.baeldung.com/2011/12/26/transaction-configuration-with-jpa-and-spring-3-1/
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}
	
	
}
