package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohActivityFactRepository;

@Repository(OhLohActivityFactRepositoryImpl.BEAN_NAME)
public class OhLohActivityFactRepositoryImpl extends BaseRepositoryImpl<Long, OhLohActivityFactEntity>
		implements OhLohActivityFactRepository {
	
	public static final String BEAN_NAME =  "ohLohActivityFactRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohActivityFactRepositoryImpl() {
		super(OhLohActivityFactEntity.class);
	}

}
