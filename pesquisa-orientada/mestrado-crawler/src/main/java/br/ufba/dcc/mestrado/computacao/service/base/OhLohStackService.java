package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.stack.OhLohStackEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackDTO;

public interface OhLohStackService extends DefaultOhLohService<OhLohStackDTO, Long, OhLohStackEntity>{

	public Long countAll();
	
	public OhLohStackEntity findById(Long id);
	
	public List<OhLohStackEntity> findAll(Integer startAt, Integer offset);
	
}
