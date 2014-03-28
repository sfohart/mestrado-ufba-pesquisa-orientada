package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohTagDTO;

public interface OhLohTagService extends DefaultOhLohService<OhLohTagDTO, Long, OhLohTagEntity>{

	public Long countAll();
	
	public OhLohTagEntity findById(Long id);
	
	public List<OhLohTagEntity> findAll(Integer startAt, Integer offset);
	
	public List<OhLohTagEntity> findTagListByName(String name);
	
}
