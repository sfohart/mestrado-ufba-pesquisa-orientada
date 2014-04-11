package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerEnlistmentEntity;

public interface OhLohCrawlerEnlistmentRepository extends BaseRepository<Long, OhLohCrawlerEnlistmentEntity>{
	
	public OhLohCrawlerEnlistmentEntity findCrawlerConfig();
	
}
