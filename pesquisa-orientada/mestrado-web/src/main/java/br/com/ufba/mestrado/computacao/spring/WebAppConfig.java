package br.com.ufba.mestrado.computacao.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

@Configuration
@EnableWebMvc
@Import({CrawlerAppConfig.class, RecommenderAppConfig.class})
public class WebAppConfig {
	
}
