package br.ufba.dcc.mestrado.computacao.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import br.ufba.dcc.mestrado.computacao.openhub.crawler.OpenHubProjectCrawler;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.search.IndexerRunner;
import br.ufba.dcc.mestrado.computacao.service.CrawlerServiceConfig;

@Configuration
@ComponentScan("br.ufba.dcc.mestrado.computacao")
@Import(value={CoreAppConfig.class, CrawlerServiceConfig.class})
@PropertySource(name = "appProperties", value = {
	"classpath:openhub-account.properties", 
	"classpath:openhub.properties" 
})
public class CrawlerAppConfig {
	@Autowired
	private Environment environment;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		
		return placeholderConfigurer;
	}

}
