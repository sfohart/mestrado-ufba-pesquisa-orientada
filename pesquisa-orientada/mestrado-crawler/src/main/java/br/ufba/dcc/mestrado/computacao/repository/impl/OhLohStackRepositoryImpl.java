package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.stack.OhLohStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohStackRepository;

@Repository
public class OhLohStackRepositoryImpl extends
		BaseRepositoryImpl<Long, OhLohStackEntity> implements OhLohStackRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohStackRepositoryImpl() {
		super(OhLohStackEntity.class);
	}

}
