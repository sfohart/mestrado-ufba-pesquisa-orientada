package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;

public interface EnlistmentRepository extends BaseRepository<Long, OhLohEnlistmentEntity>{
	
	List<OhLohEnlistmentEntity> findByProject(OhLohProjectEntity project);
	
	Long countAllByProject(OhLohProjectEntity project);
	
}
