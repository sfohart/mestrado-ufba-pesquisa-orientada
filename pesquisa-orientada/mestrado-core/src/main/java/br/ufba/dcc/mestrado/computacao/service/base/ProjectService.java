package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;
import java.util.Map;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;

public interface ProjectService extends BaseService<Long, OhLohProjectEntity>{

	Long countAll();
	
	OhLohProjectEntity findById(Long id);
	
	List<OhLohProjectEntity> findAll(Integer startAt, Integer offset);

	Map<String, List<OhLohLinkEntity>> buildLinkMapByCategory(OhLohProjectEntity project);
}
