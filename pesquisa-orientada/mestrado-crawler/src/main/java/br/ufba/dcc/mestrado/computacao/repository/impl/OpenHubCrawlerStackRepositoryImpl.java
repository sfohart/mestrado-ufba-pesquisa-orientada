package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerStackRepository;

@Repository(OpenHubCrawlerStackRepositoryImpl.BEAN_NAME)
public class OpenHubCrawlerStackRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubCrawlerStackEntity>
	implements OpenHubCrawlerStackRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerStackRepository";

	public OpenHubCrawlerStackRepositoryImpl() {
		super(OpenHubCrawlerStackEntity.class);
	}

	@Override
	public OpenHubCrawlerStackEntity findCrawlerConfig() {		
		TypedQuery<OpenHubCrawlerStackEntity> crawlerConfig = super.createSelectAllQuery();	
		
		OpenHubCrawlerStackEntity result =  null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
