package br.ufba.dcc.mestrado.computacao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import({CoreRepositoryConfig.class})
public class RecommenderRepositoryConfig {
	@Autowired
	private Environment environment;
	
	
}
