package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.account.OhLohAccountEntity;

public interface AccountRepository extends BaseRepository<Long, OhLohAccountEntity>{
	
	
	/**
	 * @param login
	 * @return
	 */
	OhLohAccountEntity findByLogin(String login);
	
}
