package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerEnlistmentRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OpenHubCrawlerEnlistmentRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerEnlistmentService;

@Service(OpenHubCrawlerEnlistmentServiceImpl.BEAN_NAME)
public class OpenHubCrawlerEnlistmentServiceImpl
		extends BaseOpenHubServiceImpl<Long, OpenHubCrawlerEnlistmentEntity>
		implements OpenHubCrawlerEnlistmentService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerEnlistmentService";

	@Autowired
	public OpenHubCrawlerEnlistmentServiceImpl(@Qualifier(OpenHubCrawlerEnlistmentRepositoryImpl.BEAN_NAME) OpenHubCrawlerEnlistmentRepository repository) {
		super(repository, OpenHubCrawlerEnlistmentEntity.class);
	}

	@Override
	public OpenHubCrawlerEnlistmentEntity findCrawlerConfig() {
		return ((OpenHubCrawlerEnlistmentRepository) getRepository()).findCrawlerConfig();
	}
	
	
	
}
