package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerProjectEntity;

public interface OhLohCrawlerProjectRepository extends BaseRepository<Long, OhLohCrawlerProjectEntity>{
	
	public OhLohCrawlerProjectEntity findCrawlerConfig();
	
}
