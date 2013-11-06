package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerProjectEntity;
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
		return crawlerConfig.getSingleResult();
	}

}
