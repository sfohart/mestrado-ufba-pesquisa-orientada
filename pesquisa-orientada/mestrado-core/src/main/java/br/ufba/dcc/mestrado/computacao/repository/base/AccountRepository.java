package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.account.OpenHubAccountEntity;

public interface AccountRepository extends BaseRepository<Long, OpenHubAccountEntity>{
	
	
	/**
	 * @param login
	 * @return
	 */
	OpenHubAccountEntity findByLogin(String login);
	
}
