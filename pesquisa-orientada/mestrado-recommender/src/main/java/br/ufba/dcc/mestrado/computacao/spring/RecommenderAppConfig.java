package br.ufba.dcc.mestrado.computacao.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import br.ufba.dcc.mestrado.computacao.main.RecommenderMain;
import br.ufba.dcc.mestrado.computacao.service.RecommenderServiceConfig;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;

@Configuration
@PropertySource(value = "classpath:persistence.properties")
@ComponentScan
@Import(RecommenderServiceConfig.class)
public class RecommenderAppConfig {
	
	@Autowired
	private RecommenderService recommenderService;

	@Bean
	public RecommenderMain recommenderMain() {
		return new RecommenderMain(recommenderService);
	}
	
}
