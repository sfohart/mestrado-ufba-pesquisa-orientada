package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerProjectEntity;

public interface OpenHubCrawlerProjectRepository extends BaseRepository<Long, OpenHubCrawlerProjectEntity>{
	
	public OpenHubCrawlerProjectEntity findCrawlerConfig();
	
}
