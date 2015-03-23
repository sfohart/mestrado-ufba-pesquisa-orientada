package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerLanguageEntity;

public interface OpenHubCrawlerLanguageRepository extends BaseRepository<Long, OpenHubCrawlerLanguageEntity>{
	
	public OpenHubCrawlerLanguageEntity findCrawlerConfig();
	
}
