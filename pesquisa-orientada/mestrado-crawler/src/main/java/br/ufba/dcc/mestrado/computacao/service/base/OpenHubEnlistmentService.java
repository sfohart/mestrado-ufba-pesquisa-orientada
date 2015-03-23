package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.enlistment.OpenHubEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.enlistment.OpenHubEnlistmentDTO;

public interface OpenHubEnlistmentService extends DefaultOpenHubService<OpenHubEnlistmentDTO, Long, OpenHubEnlistmentEntity>{

	List<OpenHubEnlistmentEntity> findByProject(OpenHubProjectEntity project);
	
	Long countAllByProject(OpenHubProjectEntity project);
	
}
