package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;

public interface LinkRepository extends BaseRepository<Long, OhLohLinkEntity>{
	
	List<OhLohLinkEntity> findByProject(OhLohProjectEntity project);
	
	Long countAllByProject(OhLohProjectEntity project);
	
}
