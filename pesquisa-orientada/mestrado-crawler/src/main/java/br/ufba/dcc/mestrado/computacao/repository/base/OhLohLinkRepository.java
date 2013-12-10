package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

public interface OhLohLinkRepository extends BaseRepository<Long, OhLohLinkEntity>{
	
	List<OhLohLinkEntity> findByProject(OhLohProjectEntity project);
	
	Long countAllByProject(OhLohProjectEntity project);
	
}
