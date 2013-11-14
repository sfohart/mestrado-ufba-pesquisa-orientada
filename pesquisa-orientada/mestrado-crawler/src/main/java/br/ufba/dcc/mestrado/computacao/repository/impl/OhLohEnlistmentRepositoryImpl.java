package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohEnlistmentRepository;

@Repository(OhLohEnlistmentRepositoryImpl.BEAN_NAME)
public class OhLohEnlistmentRepositoryImpl extends BaseRepositoryImpl<Long, OhLohEnlistmentEntity>
		implements OhLohEnlistmentRepository {
	
	public static final String BEAN_NAME =  "ohLohEnlistmentRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohEnlistmentRepositoryImpl() {
		super(OhLohEnlistmentEntity.class);
	}

}
