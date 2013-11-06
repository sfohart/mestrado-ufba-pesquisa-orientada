package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAccountRepository;

@Repository(OhLohAccountRepositoryImpl.BEAN_NAME)
public class OhLohAccountRepositoryImpl extends BaseRepositoryImpl<Long, OhLohAccountEntity>
		implements OhLohAccountRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohAccountRepository";

	public OhLohAccountRepositoryImpl() {
		super(OhLohAccountEntity.class);
	}

	
}
