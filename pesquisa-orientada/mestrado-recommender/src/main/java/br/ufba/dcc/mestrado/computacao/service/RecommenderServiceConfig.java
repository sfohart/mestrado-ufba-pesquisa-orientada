package br.ufba.dcc.mestrado.computacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.ufba.dcc.mestrado.computacao.repository.RecommenderRepositoryConfig;
import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRecommenderCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderService;
import br.ufba.dcc.mestrado.computacao.service.impl.RecommenderServiceImpl;

@Configuration
@Import(RecommenderRepositoryConfig.class)
public class RecommenderServiceConfig {
	
	@Autowired
	private CriteriumPreferenceRepository criteriumPreferenceRepository;
	
	@Autowired
	private RecommenderCriteriumRepository recommenderCriteriumRepository;
	
	@Autowired
	private UserRecommenderCriteriumRepository userRecommenderCriteriumRepository;
	
	@Bean
	public RecommenderService recommenderService() {
		return new RecommenderServiceImpl(
				criteriumPreferenceRepository,
				recommenderCriteriumRepository,
				userRecommenderCriteriumRepository);
	}

}
