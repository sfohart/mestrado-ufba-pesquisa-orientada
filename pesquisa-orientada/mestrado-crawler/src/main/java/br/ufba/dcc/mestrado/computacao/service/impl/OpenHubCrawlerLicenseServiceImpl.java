package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerLicenseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerLicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.OpenHubCrawlerLicenseRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubCrawlerLicenseService;

@Service(OpenHubCrawlerLicenseServiceImpl.BEAN_NAME)
public class OpenHubCrawlerLicenseServiceImpl
		extends BaseOpenHubServiceImpl<Long, OpenHubCrawlerLicenseEntity>
		implements OpenHubCrawlerLicenseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608418368196004751L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerLicenseService";

	@Autowired
	public OpenHubCrawlerLicenseServiceImpl(@Qualifier(OpenHubCrawlerLicenseRepositoryImpl.BEAN_NAME) OpenHubCrawlerLicenseRepository repository) {
		super(repository, OpenHubCrawlerLicenseEntity.class);
	}

	@Override
	public OpenHubCrawlerLicenseEntity findCrawlerConfig() {
		return ((OpenHubCrawlerLicenseRepository) getRepository()).findCrawlerConfig();
	}
	
	
	
}
