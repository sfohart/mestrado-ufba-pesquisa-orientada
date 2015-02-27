package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OhLohCrawlerLanguageEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohCrawlerLanguageRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerLanguageService;

@Service(OhLohCrawlerLanguageServiceImpl.BEAN_NAME)
public class OhLohCrawlerLanguageServiceImpl
		extends BaseOhLohServiceImpl<Long, OhLohCrawlerLanguageEntity>
		implements OhLohCrawlerLanguageService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerLanguageService";

	@Autowired
	public OhLohCrawlerLanguageServiceImpl(@Qualifier(OhLohCrawlerLanguageRepositoryImpl.BEAN_NAME) OhLohCrawlerLanguageRepository repository) {
		super(repository, OhLohCrawlerLanguageEntity.class);
	}

	@Override
	public OhLohCrawlerLanguageEntity findCrawlerConfig() {
		return ((OhLohCrawlerLanguageRepository) getRepository()).findCrawlerConfig();
	}
	
	
	
}
