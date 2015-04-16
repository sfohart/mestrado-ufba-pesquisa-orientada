package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerEnlistmentRepository;

@Repository(OpenHubCrawlerEnlistmentRepositoryImpl.BEAN_NAME)
public class OpenHubCrawlerEnlistmentRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubCrawlerEnlistmentEntity>
	implements OpenHubCrawlerEnlistmentRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerEnlistmentRepository";

	public OpenHubCrawlerEnlistmentRepositoryImpl() {
		super(OpenHubCrawlerEnlistmentEntity.class);
	}

	@Override
	public OpenHubCrawlerEnlistmentEntity findCrawlerConfig() {		
		TypedQuery<OpenHubCrawlerEnlistmentEntity> crawlerConfig = super.createSelectAllQuery();		
		OpenHubCrawlerEnlistmentEntity result = null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
