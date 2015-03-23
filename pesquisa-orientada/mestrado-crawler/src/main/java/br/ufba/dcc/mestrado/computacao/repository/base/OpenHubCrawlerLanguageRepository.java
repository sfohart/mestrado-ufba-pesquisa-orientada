package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerLanguageEntity;

public interface OpenHubCrawlerLanguageRepository extends BaseRepository<Long, OpenHubCrawlerLanguageEntity>{
	
	public OpenHubCrawlerLanguageEntity findCrawlerConfig();
	
}
