package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectActivityIndexEntity;

public interface ProjectActivityIndexService extends BaseService<Long, OpenHubProjectActivityIndexEntity>{
	
	public OpenHubProjectActivityIndexEntity findByValue(Long value);
	
}
