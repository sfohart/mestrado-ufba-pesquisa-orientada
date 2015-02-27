package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OhLohCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OhLohCrawlerProjectRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerProjectService;

@Service(OhLohCrawlerProjectServiceImpl.BEAN_NAME)
public class OhLohCrawlerProjectServiceImpl
		extends BaseOhLohServiceImpl<Long, OhLohCrawlerProjectEntity>
		implements OhLohCrawlerProjectService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerProjectService";

	@Autowired
	public OhLohCrawlerProjectServiceImpl(@Qualifier(OhLohCrawlerProjectRepositoryImpl.BEAN_NAME) OhLohCrawlerProjectRepository repository) {
		super(repository, OhLohCrawlerProjectEntity.class);
	}
	
	@Override
	public OhLohCrawlerProjectEntity findCrawlerConfig() {
		return ((OhLohCrawlerProjectRepository) getRepository()).findCrawlerConfig();
	}
	
}
