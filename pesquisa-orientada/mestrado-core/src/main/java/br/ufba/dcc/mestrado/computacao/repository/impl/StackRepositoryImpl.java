package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.stack.OhLohStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.StackRepository;

@Repository(StackRepositoryImpl.BEAN_NAME)
public class StackRepositoryImpl extends
		BaseRepositoryImpl<Long, OhLohStackEntity> implements StackRepository {

	public static final String BEAN_NAME =  "stackRepository";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public StackRepositoryImpl() {
		super(OhLohStackEntity.class);
	}

}
