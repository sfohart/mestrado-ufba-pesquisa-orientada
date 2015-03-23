package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerLanguageEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OpenHubCrawlerLanguageRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerLanguageService;

@Service(OpenHubCrawlerLanguageServiceImpl.BEAN_NAME)
public class OpenHubCrawlerLanguageServiceImpl
		extends BaseOpenHubServiceImpl<Long, OpenHubCrawlerLanguageEntity>
		implements OpenHubCrawlerLanguageService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerLanguageService";

	@Autowired
	public OpenHubCrawlerLanguageServiceImpl(@Qualifier(OpenHubCrawlerLanguageRepositoryImpl.BEAN_NAME) OpenHubCrawlerLanguageRepository repository) {
		super(repository, OpenHubCrawlerLanguageEntity.class);
	}

	@Override
	public OpenHubCrawlerLanguageEntity findCrawlerConfig() {
		return ((OpenHubCrawlerLanguageRepository) getRepository()).findCrawlerConfig();
	}
	
	
	
}
