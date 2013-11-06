package br.ufba.dcc.mestrado.computacao.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.ufba.dcc.mestrado.computacao.main.RecommenderMain;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaDataModelBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.impl.DefaultMultiCriteriaDataModelBuilder;
import br.ufba.dcc.mestrado.computacao.service.RecommenderServiceConfig;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;

@Configuration
@ComponentScan
@Import(RecommenderServiceConfig.class)
public class RecommenderAppConfig {
	
	@Autowired
	private RecommenderService recommenderService;

	@Bean
	public RecommenderMain recommenderMain() {
		return new RecommenderMain(recommenderService);
	}
	
	@Bean
	public MultiCriteriaDataModelBuilder defaultMultiCriteriaDataModelBuilder() {
		return new DefaultMultiCriteriaDataModelBuilder();
	}
	
}
