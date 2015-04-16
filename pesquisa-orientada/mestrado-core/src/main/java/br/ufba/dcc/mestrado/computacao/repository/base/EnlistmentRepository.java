package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.enlistment.OpenHubEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public interface EnlistmentRepository extends BaseRepository<Long, OpenHubEnlistmentEntity>{
	
	List<OpenHubEnlistmentEntity> findByProject(OpenHubProjectEntity project);
	
	Long countAllByProject(OpenHubProjectEntity project);
	
}
