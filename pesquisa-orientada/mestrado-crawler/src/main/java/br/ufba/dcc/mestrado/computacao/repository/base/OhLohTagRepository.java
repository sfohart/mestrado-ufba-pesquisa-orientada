package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;

public interface OhLohTagRepository extends BaseRepository<Long, OhLohTagEntity>{

	public OhLohTagEntity findByName(String name);

	public List<OhLohTagEntity> findTagListByName(String name);
	
}
