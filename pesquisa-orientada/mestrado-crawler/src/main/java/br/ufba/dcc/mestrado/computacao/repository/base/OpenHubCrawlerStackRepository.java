package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerStackEntity;

public interface OpenHubCrawlerStackRepository extends BaseRepository<Long, OpenHubCrawlerStackEntity>{
	
	public OpenHubCrawlerStackEntity findCrawlerConfig();
	
}
