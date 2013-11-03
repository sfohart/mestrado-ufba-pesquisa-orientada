package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

public interface OhLohProjectRepository extends BaseRepository<Long, OhLohProjectEntity>{

	OhLohProjectEntity findByName(String name);
	
	List<OhLohProjectEntity> findAllByFullTextQuery(String query);
}
