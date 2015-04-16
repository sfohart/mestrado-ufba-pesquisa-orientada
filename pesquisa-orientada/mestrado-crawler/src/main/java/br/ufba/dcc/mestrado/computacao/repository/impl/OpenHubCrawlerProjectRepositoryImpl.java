package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OpenHubCrawlerProjectRepository;

@Repository(OpenHubCrawlerProjectRepositoryImpl.BEAN_NAME)
public class OpenHubCrawlerProjectRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubCrawlerProjectEntity>
	implements OpenHubCrawlerProjectRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerProjectRepository";

	public OpenHubCrawlerProjectRepositoryImpl() {
		super(OpenHubCrawlerProjectEntity.class);
	}

	@Override
	public OpenHubCrawlerProjectEntity findCrawlerConfig() {		
		TypedQuery<OpenHubCrawlerProjectEntity> crawlerConfig = super.createSelectAllQuery();	
		
		OpenHubCrawlerProjectEntity result =  null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
