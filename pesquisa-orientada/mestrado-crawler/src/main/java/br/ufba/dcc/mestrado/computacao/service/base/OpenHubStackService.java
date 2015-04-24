package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.stack.OpenHubStackEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.stack.OpenHubStackDTO;

public interface OpenHubStackService extends DefaultOpenHubService<OpenHubStackDTO, Long, OpenHubStackEntity>{

	public Long countAll();
	
	public OpenHubStackEntity findById(Long id);
	
	public List<OpenHubStackEntity> findAll(Integer startAt, Integer offset);
	
}
