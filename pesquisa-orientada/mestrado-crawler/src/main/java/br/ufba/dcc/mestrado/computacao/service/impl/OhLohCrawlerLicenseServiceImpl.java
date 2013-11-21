package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerLicenseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerLicenseRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohCrawlerLicenseService;

@Service(OhLohCrawlerLicenseServiceImpl.BEAN_NAME)
public class OhLohCrawlerLicenseServiceImpl
		extends BaseOhLohServiceImpl<Long, OhLohCrawlerLicenseEntity>
		implements OhLohCrawlerLicenseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerLicenseService";

	@Autowired
	public OhLohCrawlerLicenseServiceImpl(@Qualifier("ohLohCrawlerLicenseRepository") OhLohCrawlerLicenseRepository repository) {
		super(repository, OhLohCrawlerLicenseEntity.class);
	}

	@Override
	public OhLohCrawlerLicenseEntity findCrawlerConfig() {
		return ((OhLohCrawlerLicenseRepository) getRepository()).findCrawlerConfig();
	}
	
	
	
}
