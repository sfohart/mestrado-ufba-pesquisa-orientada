package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLinkRepository;

@Repository(OhLohLinkRepositoryImpl.BEAN_NAME)
public class OhLohLinkRepositoryImpl extends BaseRepositoryImpl<Long, OhLohLinkEntity>
		implements OhLohLinkRepository {
	
	public static final String BEAN_NAME =  "ohLohLinkRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohLinkRepositoryImpl() {
		super(OhLohLinkEntity.class);
	}

}
