package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerLanguageEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerLanguageRepository;

@Repository(OpenHubCrawlerLanguageRepositoryImpl.BEAN_NAME)
public class OpenHubCrawlerLanguageRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubCrawlerLanguageEntity>
	implements OpenHubCrawlerLanguageRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerLanguageRepository";

	public OpenHubCrawlerLanguageRepositoryImpl() {
		super(OpenHubCrawlerLanguageEntity.class);
	}

	@Override
	public OpenHubCrawlerLanguageEntity findCrawlerConfig() {		
		TypedQuery<OpenHubCrawlerLanguageEntity> crawlerConfig = super.createSelectAllQuery();		
		OpenHubCrawlerLanguageEntity result = null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
