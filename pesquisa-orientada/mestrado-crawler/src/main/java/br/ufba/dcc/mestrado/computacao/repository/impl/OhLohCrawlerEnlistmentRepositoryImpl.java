package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohCrawlerEnlistmentRepository;

@Repository(OhLohCrawlerEnlistmentRepositoryImpl.BEAN_NAME)
public class OhLohCrawlerEnlistmentRepositoryImpl extends BaseRepositoryImpl<Long, OhLohCrawlerEnlistmentEntity>
	implements OhLohCrawlerEnlistmentRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohCrawlerEnlistmentRepository";

	public OhLohCrawlerEnlistmentRepositoryImpl() {
		super(OhLohCrawlerEnlistmentEntity.class);
	}

	@Override
	public OhLohCrawlerEnlistmentEntity findCrawlerConfig() {		
		TypedQuery<OhLohCrawlerEnlistmentEntity> crawlerConfig = super.createSelectAllQuery();		
		OhLohCrawlerEnlistmentEntity result = null;
		
		try {
			result = crawlerConfig.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		return result;
	}

}
