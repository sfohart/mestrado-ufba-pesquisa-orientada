package br.ufba.dcc.mestrado.computacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.ufba.dcc.mestrado.computacao.repository.CrawlerRepositoryConfig;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAccountRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohStackRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAccountService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLanguageService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohStackService;
import br.ufba.dcc.mestrado.computacao.service.impl.OhLohAccountServiceImpl;
import br.ufba.dcc.mestrado.computacao.service.impl.OhLohAnalysisServiceImpl;
import br.ufba.dcc.mestrado.computacao.service.impl.OhLohLanguageServiceImpl;
import br.ufba.dcc.mestrado.computacao.service.impl.OhLohProjectServiceImpl;
import br.ufba.dcc.mestrado.computacao.service.impl.OhLohStackServiceImpl;

@Configuration
@Import(CrawlerRepositoryConfig.class)
public class CrawlerServiceConfig {
	
	@Autowired
	private OhLohAccountRepository ohLohAccountRepository;
	
	@Autowired
	private OhLohAnalysisRepository ohLohAnalysisRepository;
	
	@Autowired
	private OhLohLanguageRepository ohLohLanguageRepository;
	
	@Autowired
	private OhLohProjectRepository ohLohProjectRepository;
	
	@Autowired
	private OhLohStackRepository ohLohStackRepository;
	
	@Bean
	public OhLohAccountService ohLohAccountService() {
		return new OhLohAccountServiceImpl(ohLohAccountRepository);
	}
	
	@Bean
	public OhLohAnalysisService ohLohAnalysisService() {
		return new OhLohAnalysisServiceImpl(ohLohAnalysisRepository);
	}
	
	@Bean
	public OhLohLanguageService ohLohLanguageService() {
		return new OhLohLanguageServiceImpl(ohLohLanguageRepository);
	}
	
	@Bean
	public OhLohProjectService ohLohProjectService() {
		return new OhLohProjectServiceImpl(ohLohProjectRepository);
	}
	
	@Bean
	public OhLohStackService ohLohStackService() {
		return new OhLohStackServiceImpl(ohLohStackRepository);
	}

}
