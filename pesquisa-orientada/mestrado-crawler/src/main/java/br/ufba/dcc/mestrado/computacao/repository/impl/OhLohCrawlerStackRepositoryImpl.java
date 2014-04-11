package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerStackRepository;

@Repository(OhLohCrawlerStackRepositoryImpl.BEAN_NAME)
public class OhLohCrawlerStackRepositoryImpl extends BaseRepositoryImpl<Long, OhLohCrawlerStackEntity>
	implements OhLohCrawlerStackRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerStackRepository";

	public OhLohCrawlerStackRepositoryImpl() {
		super(OhLohCrawlerStackEntity.class);
	}

	@Override
	public OhLohCrawlerStackEntity findCrawlerConfig() {		
		TypedQuery<OhLohCrawlerStackEntity> crawlerConfig = super.createSelectAllQuery();	
		
		OhLohCrawlerStackEntity result =  null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
