package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.account.OpenHubAccountEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountDTO;

public interface OpenHubAccountService extends DefaultOpenHubService<OpenHubAccountDTO, Long, OpenHubAccountEntity>{

	Long countAll();
	
	OpenHubAccountEntity findById(Long id);
	
	List<OpenHubAccountEntity> findAll(Integer startAt, Integer offset);
	
	OpenHubAccountEntity findByLogin(String login);
	
}
