package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OpenHubCrawlerProjectRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerProjectService;

@Service(OpenHubCrawlerProjectServiceImpl.BEAN_NAME)
public class OpenHubCrawlerProjectServiceImpl
		extends BaseOpenHubServiceImpl<Long, OpenHubCrawlerProjectEntity>
		implements OpenHubCrawlerProjectService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerProjectService";

	@Autowired
	public OpenHubCrawlerProjectServiceImpl(@Qualifier(OpenHubCrawlerProjectRepositoryImpl.BEAN_NAME) OpenHubCrawlerProjectRepository repository) {
		super(repository, OpenHubCrawlerProjectEntity.class);
	}
	
	@Override
	public OpenHubCrawlerProjectEntity findCrawlerConfig() {
		return ((OpenHubCrawlerProjectRepository) getRepository()).findCrawlerConfig();
	}
	
}
