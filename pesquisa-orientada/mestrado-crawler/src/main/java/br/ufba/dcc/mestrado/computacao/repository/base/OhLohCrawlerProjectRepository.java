package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerProjectEntity;

public interface OhLohCrawlerProjectRepository extends BaseRepository<Long, OhLohCrawlerProjectEntity>{
	
	public OhLohCrawlerProjectEntity findCrawlerConfig();
	
}
