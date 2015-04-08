package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;

public interface TagService extends BaseService<Long, OpenHubTagEntity>{

	public Long countAll();
	
	public OpenHubTagEntity findById(Long id);
	
	public List<OpenHubTagEntity> findAll(Integer startAt, Integer offset);
	
	public OpenHubTagEntity findByName(String name);
	
	public List<OpenHubTagEntity> findTagListByName(String name);
	
}
