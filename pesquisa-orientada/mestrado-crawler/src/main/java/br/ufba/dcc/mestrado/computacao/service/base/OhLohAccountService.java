package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountDTO;

public interface OhLohAccountService extends DefaultOhLohService<OhLohAccountDTO, Long, OhLohAccountEntity>{

	Long countAll();
	
	OhLohAccountEntity findById(Long id);
	
	List<OhLohAccountEntity> findAll(Integer startAt, Integer offset);
	
	OhLohAccountEntity findByLogin(String login);
	
}
