package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerStackEntity;

public interface OhLohCrawlerStackRepository extends BaseRepository<Long, OhLohCrawlerStackEntity>{
	
	public OhLohCrawlerStackEntity findCrawlerConfig();
	
}
