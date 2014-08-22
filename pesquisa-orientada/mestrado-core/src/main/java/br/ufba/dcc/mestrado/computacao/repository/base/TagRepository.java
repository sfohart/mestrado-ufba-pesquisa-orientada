package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohTagEntity;

public interface TagRepository extends BaseRepository<Long, OhLohTagEntity>{

	public OhLohTagEntity findByName(String name);

	public List<OhLohTagEntity> findTagListByName(String name);
	
}
