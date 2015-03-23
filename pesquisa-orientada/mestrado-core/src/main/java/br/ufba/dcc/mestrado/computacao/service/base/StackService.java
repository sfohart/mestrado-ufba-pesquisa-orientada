package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.stack.OpenHubStackEntity;

public interface StackService extends BaseService<Long, OpenHubStackEntity>{

	public Long countAll();
	
	public OpenHubStackEntity findById(Long id);
	
	public List<OpenHubStackEntity> findAll(Integer startAt, Integer offset);
	
}
