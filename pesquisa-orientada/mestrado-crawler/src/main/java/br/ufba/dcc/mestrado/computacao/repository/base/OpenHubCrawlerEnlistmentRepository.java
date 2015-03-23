package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerEnlistmentEntity;

public interface OpenHubCrawlerEnlistmentRepository extends BaseRepository<Long, OpenHubCrawlerEnlistmentEntity>{
	
	public OpenHubCrawlerEnlistmentEntity findCrawlerConfig();
	
}
