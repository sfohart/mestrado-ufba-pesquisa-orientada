package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerLicenseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerLicenseRepository;

@Repository(OpenHubCrawlerLicenseRepositoryImpl.BEAN_NAME)
public class OpenHubCrawlerLicenseRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubCrawlerLicenseEntity>
	implements OpenHubCrawlerLicenseRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerLicenseRepository";

	public OpenHubCrawlerLicenseRepositoryImpl() {
		super(OpenHubCrawlerLicenseEntity.class);
	}

	@Override
	public OpenHubCrawlerLicenseEntity findCrawlerConfig() {		
		TypedQuery<OpenHubCrawlerLicenseEntity> crawlerConfig = super.createSelectAllQuery();		
		OpenHubCrawlerLicenseEntity result = null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
