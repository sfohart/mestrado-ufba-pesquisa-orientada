package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerStackRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OpenHubCrawlerStackRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerStackService;

@Service(OpenHubCrawlerStackServiceImpl.BEAN_NAME)
public class OpenHubCrawlerStackServiceImpl
		extends BaseOpenHubServiceImpl<Long, OpenHubCrawlerStackEntity>
		implements OpenHubCrawlerStackService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerStackService";

	@Autowired
	public OpenHubCrawlerStackServiceImpl(@Qualifier(OpenHubCrawlerStackRepositoryImpl.BEAN_NAME) OpenHubCrawlerStackRepository repository) {
		super(repository, OpenHubCrawlerStackEntity.class);
	}
	
	@Override
	public OpenHubCrawlerStackEntity findCrawlerConfig() {
		return ((OpenHubCrawlerStackRepository) getRepository()).findCrawlerConfig();
	}
	
}
