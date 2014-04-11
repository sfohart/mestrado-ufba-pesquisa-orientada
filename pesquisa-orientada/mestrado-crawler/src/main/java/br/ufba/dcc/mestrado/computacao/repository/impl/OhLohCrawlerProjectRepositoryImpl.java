package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerProjectRepository;

@Repository(OhLohCrawlerProjectRepositoryImpl.BEAN_NAME)
public class OhLohCrawlerProjectRepositoryImpl extends BaseRepositoryImpl<Long, OhLohCrawlerProjectEntity>
	implements OhLohCrawlerProjectRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerProjectRepository";

	public OhLohCrawlerProjectRepositoryImpl() {
		super(OhLohCrawlerProjectEntity.class);
	}

	@Override
	public OhLohCrawlerProjectEntity findCrawlerConfig() {		
		TypedQuery<OhLohCrawlerProjectEntity> crawlerConfig = super.createSelectAllQuery();	
		
		OhLohCrawlerProjectEntity result =  null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
