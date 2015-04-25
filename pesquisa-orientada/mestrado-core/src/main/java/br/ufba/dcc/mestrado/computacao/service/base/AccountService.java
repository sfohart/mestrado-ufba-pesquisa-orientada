package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.account.OpenHubAccountEntity;

public interface AccountService extends BaseService<Long, OpenHubAccountEntity>{

	Long countAll();
	
	OpenHubAccountEntity findById(Long id);
	
	List<OpenHubAccountEntity> findAll(Integer startAt, Integer offset);
	
	OpenHubAccountEntity findByLogin(String login);
	
}
