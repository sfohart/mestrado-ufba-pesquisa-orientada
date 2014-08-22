package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.stack.OhLohStackEntity;

public interface StackService extends BaseService<Long, OhLohStackEntity>{

	public Long countAll();
	
	public OhLohStackEntity findById(Long id);
	
	public List<OhLohStackEntity> findAll(Integer startAt, Integer offset);
	
}
