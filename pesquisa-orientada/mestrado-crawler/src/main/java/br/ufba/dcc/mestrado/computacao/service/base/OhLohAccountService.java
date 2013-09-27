package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountDTO;

public interface OhLohAccountService extends BaseOhLohService<OhLohAccountDTO, Long, OhLohAccountEntity>{

	public Long countAll();
	
	public OhLohAccountEntity findById(Long id);
	
	public List<OhLohAccountEntity> findAll(Integer startAt, Integer offset);
	
}
