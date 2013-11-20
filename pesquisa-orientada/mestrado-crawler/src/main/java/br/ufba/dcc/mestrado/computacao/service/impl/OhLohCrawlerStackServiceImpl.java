package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerStackRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerStackService;

@Service(OhLohCrawlerStackServiceImpl.BEAN_NAME)
public class OhLohCrawlerStackServiceImpl
		extends BaseOhLohServiceImpl<Long, OhLohCrawlerStackEntity>
		implements OhLohCrawlerStackService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerStackService";

	@Autowired
	public OhLohCrawlerStackServiceImpl(@Qualifier("ohLohCrawlerStackRepository") OhLohCrawlerStackRepository repository) {
		super(repository, OhLohCrawlerStackEntity.class);
	}
	
	@Override
	public OhLohCrawlerStackEntity findCrawlerConfig() {
		return ((OhLohCrawlerStackRepository) getRepository()).findCrawlerConfig();
	}
	
}
