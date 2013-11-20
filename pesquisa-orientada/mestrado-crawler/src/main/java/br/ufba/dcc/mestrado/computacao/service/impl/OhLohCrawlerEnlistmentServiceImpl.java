package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerEnlistmentRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerEnlistmentService;

@Service(OhLohCrawlerEnlistmentServiceImpl.BEAN_NAME)
public class OhLohCrawlerEnlistmentServiceImpl
		extends BaseOhLohServiceImpl<Long, OhLohCrawlerEnlistmentEntity>
		implements OhLohCrawlerEnlistmentService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerEnlistmentService";

	@Autowired
	public OhLohCrawlerEnlistmentServiceImpl(@Qualifier("ohLohCrawlerEnlistmentRepository") OhLohCrawlerEnlistmentRepository repository) {
		super(repository, OhLohCrawlerEnlistmentEntity.class);
	}

	@Override
	public OhLohCrawlerEnlistmentEntity findCrawlerConfig() {
		return ((OhLohCrawlerEnlistmentRepository) getRepository()).findCrawlerConfig();
	}
	
	
	
}
