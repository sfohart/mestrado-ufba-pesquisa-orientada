package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import br.ufba.dcc.mestrado.computacao.ohloh.crawler.OhLohCrawler;
import br.ufba.dcc.mestrado.computacao.ohloh.crawler.OhLohProjectCrawler;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.search.IndexerRunner;
import br.ufba.dcc.mestrado.computacao.service.CrawlerServiceConfig;

@Configuration
@ComponentScan("br.ufba.dcc.mestrado.computacao")
@Import(CrawlerServiceConfig.class)
@PropertySource(name = "appProperties", value = {
		"classpath:ohloh-account.properties", "classpath:ohloh.properties" })
public class CrawlerAppConfig {
	@Autowired
	private Environment environment;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		
		return placeholderConfigurer;
	}

	@Bean
	public IndexerRunner indexerRunner() {
		return new IndexerRunner();
	}
	
	@Bean
	public OhLohCrawler ohLohCrawler() {
		return new OhLohCrawler();
	}

	@Bean
	public OhLohProjectCrawler ohLohProjectCrawler() {
		return new OhLohProjectCrawler();
	}

	@Bean
	public OhLohRestfulClient ohLohRestfulClient() {
		return new OhLohRestfulClient();
	}

}
