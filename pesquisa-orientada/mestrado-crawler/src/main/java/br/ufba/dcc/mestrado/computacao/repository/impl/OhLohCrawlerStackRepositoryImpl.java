package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerStackRepository;

@Repository
public class OhLohCrawlerStackRepositoryImpl extends BaseRepositoryImpl<Long, OhLohCrawlerStackEntity>
	implements OhLohCrawlerStackRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

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
