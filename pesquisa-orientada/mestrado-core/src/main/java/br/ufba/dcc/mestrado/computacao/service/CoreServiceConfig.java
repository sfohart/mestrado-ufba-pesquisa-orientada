package br.ufba.dcc.mestrado.computacao.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.ufba.dcc.mestrado.computacao.repository.CoreRepositoryConfig;

@Configuration
@Import(CoreRepositoryConfig.class)
public class CoreServiceConfig {
	
}
