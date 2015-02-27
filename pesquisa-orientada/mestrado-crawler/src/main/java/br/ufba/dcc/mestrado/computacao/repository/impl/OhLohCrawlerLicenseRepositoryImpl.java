package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OhLohCrawlerLicenseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerLicenseRepository;

@Repository(OhLohCrawlerLicenseRepositoryImpl.BEAN_NAME)
public class OhLohCrawlerLicenseRepositoryImpl extends BaseRepositoryImpl<Long, OhLohCrawlerLicenseEntity>
	implements OhLohCrawlerLicenseRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerLicenseRepository";

	public OhLohCrawlerLicenseRepositoryImpl() {
		super(OhLohCrawlerLicenseEntity.class);
	}

	@Override
	public OhLohCrawlerLicenseEntity findCrawlerConfig() {		
		TypedQuery<OhLohCrawlerLicenseEntity> crawlerConfig = super.createSelectAllQuery();		
		OhLohCrawlerLicenseEntity result = null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
