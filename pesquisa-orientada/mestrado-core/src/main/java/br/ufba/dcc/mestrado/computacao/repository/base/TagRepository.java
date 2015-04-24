package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;

public interface TagRepository extends BaseRepository<Long, OpenHubTagEntity>{

	public OpenHubTagEntity findByName(String name);

	public List<OpenHubTagEntity> findTagListByName(String name);
	
}
