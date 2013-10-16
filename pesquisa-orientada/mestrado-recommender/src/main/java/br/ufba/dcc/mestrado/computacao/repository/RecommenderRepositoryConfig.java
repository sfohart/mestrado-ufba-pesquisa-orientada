package br.ufba.dcc.mestrado.computacao.repository;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAccountRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.CriteriumPreferenceRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohAccountRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.RecommenderCriteriumRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.UserRecommenderCriteriumRepositoryImpl;

@Configuration
@PropertySource(value = "classpath:persistence.properties")
public class RecommenderRepositoryConfig {
	@Autowired
	private Environment environment;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setUrl(environment.getProperty("javax.persistence.jdbc.url"));
		dataSource.setUsername(environment.getProperty("javax.persistence.jdbc.user"));
		dataSource.setPassword(environment.getProperty("javax.persistence.jdbc.password"));
		dataSource.setDriverClassName(environment.getProperty("javax.persistence.jdbc.driver"));
		
		return dataSource; 
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean  entityManagerFactoryBean() {
		
		Properties connectionProperties = new Properties();
		
		connectionProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		connectionProperties.put("hibernate.cache.provider_class", environment.getProperty("hibernate.cache.provider_class"));
		connectionProperties.put("hibernate.jdbc.batch_size", environment.getProperty("hibernate.jdbc.batch_size"));
		
		connectionProperties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		connectionProperties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
		connectionProperties.put("hibernate.use_sql_comments", environment.getProperty("hibernate.use_sql_comments"));
		connectionProperties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		
		LocalContainerEntityManagerFactoryBean  entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setPersistenceUnitName(environment.getProperty("persistenceUnitName"));
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaProperties(connectionProperties);
		
		return entityManagerFactoryBean;
	}
	
	@Bean
	public CriteriumPreferenceRepository criteriumPreferenceRepository() {
		return new CriteriumPreferenceRepositoryImpl();
	}
	
	@Bean
	public RecommenderCriteriumRepository recommenderCriteriumRepository() {
		return new RecommenderCriteriumRepositoryImpl();
	}
	
	@Bean
	public UserRecommenderCriteriumRepository userRecommenderCriteriumRepository() {
		return new UserRecommenderCriteriumRepositoryImpl();
	}
	
	@Bean 
	public OhLohAccountRepository ohLohAccountRepository() {
		return new OhLohAccountRepositoryImpl(); 
	}
}
