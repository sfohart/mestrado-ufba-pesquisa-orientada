package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;

public interface TagService extends BaseService<Long, OhLohTagEntity>{

	public Long countAll();
	
	public OhLohTagEntity findById(Long id);
	
	public List<OhLohTagEntity> findAll(Integer startAt, Integer offset);
	
	public OhLohTagEntity findByName(String name);
	
	public List<OhLohTagEntity> findTagListByName(String name);
	
}
