package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohLinkDTO;

public interface OhLohLinkService extends DefaultOhLohService<OhLohLinkDTO, Long, OhLohLinkEntity>{

	
	List<OhLohLinkEntity> findByProject(OhLohProjectEntity project);
	
	Long countAllByProject(OhLohProjectEntity project);
	
}
