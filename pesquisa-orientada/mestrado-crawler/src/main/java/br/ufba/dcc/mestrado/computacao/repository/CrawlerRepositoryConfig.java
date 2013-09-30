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

import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAccountRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisLanguagesRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerStackRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohStackRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohTagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohAccountRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohAnalysisLanguageRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohAnalysisLanguagesRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohAnalysisRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohCrawlerLanguageRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohCrawlerProjectRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohCrawlerStackRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohLanguageRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohLicenseRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohProjectRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohStackRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohTagRepositoryImpl;

@Configuration
@PropertySource(value = {	
	"classpath:persistence.properties"
})
public class CrawlerRepositoryConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setUrl(env.getProperty("javax.persistence.jdbc.url"));
		dataSource.setUsername(env.getProperty("javax.persistence.jdbc.user"));
		dataSource.setPassword(env.getProperty("javax.persistence.jdbc.password"));
		dataSource.setDriverClassName(env.getProperty("javax.persistence.jdbc.driver"));
		
		return dataSource; 
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean  entityManagerFactoryBean() {
		
		Properties connectionProperties = new Properties();
		
		connectionProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		connectionProperties.put("hibernate.cache.provider_class", env.getProperty("hibernate.cache.provider_class"));
		connectionProperties.put("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
		
		connectionProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		connectionProperties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		connectionProperties.put("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
		connectionProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		
		LocalContainerEntityManagerFactoryBean  entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setPersistenceUnitName(env.getProperty("persistenceUnitName"));
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaProperties(connectionProperties);
		
		return entityManagerFactoryBean;
	}
	
	@Bean
	public OhLohAccountRepository ohLohAccountRepository() {
		return new OhLohAccountRepositoryImpl();
	}
	
	@Bean
	public OhLohAnalysisLanguageRepository ohLohAnalysisLanguageRepository() {
		return new OhLohAnalysisLanguageRepositoryImpl();
	}
	
	@Bean
	public OhLohAnalysisLanguagesRepository ohLohAnalysisLanguagesRepository() {
		return new OhLohAnalysisLanguagesRepositoryImpl();
	}
	
	@Bean
	public OhLohAnalysisRepository ohLohAnalysisRepository() {
		return new OhLohAnalysisRepositoryImpl();
	}
	
	@Bean
	public OhLohCrawlerLanguageRepository ohLohCrawlerLanguageRepository() {
		return new OhLohCrawlerLanguageRepositoryImpl();
	}
	
	@Bean
	public OhLohCrawlerProjectRepository ohLohCrawlerProjectRepository() {
		return new OhLohCrawlerProjectRepositoryImpl();
	}
	
	@Bean
	public OhLohCrawlerStackRepository ohLohCrawlerStackRepository() {
		return new OhLohCrawlerStackRepositoryImpl();
	}
	
	@Bean
	public OhLohLanguageRepository ohLohLanguageRepository() {
		return new OhLohLanguageRepositoryImpl();
	}
	
	@Bean
	public OhLohLicenseRepository ohLohLicenseRepository() {
		return new OhLohLicenseRepositoryImpl();
	}
	
	@Bean
	public OhLohProjectRepository ohLohProjectRepository() {
		return new OhLohProjectRepositoryImpl();
	}
	
	@Bean
	public OhLohStackRepository ohLohStackRepository() {
		return new OhLohStackRepositoryImpl();
	}
	
	@Bean
	public OhLohTagRepository ohLohTagRepository() {
		return new OhLohTagRepositoryImpl();
	}
	
}
