package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectActivityIndexEntity;

public interface ProjectActivityIndexService extends BaseService<Long, OhLohProjectActivityIndexEntity>{
	
	public OhLohProjectActivityIndexEntity findByValue(Long value);
	
}
