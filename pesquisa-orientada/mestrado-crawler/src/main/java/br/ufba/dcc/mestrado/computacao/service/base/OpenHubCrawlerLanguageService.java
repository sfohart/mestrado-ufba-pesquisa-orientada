package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerLanguageEntity;

public interface OpenHubCrawlerLanguageService extends BaseOpenHubService<Long, OpenHubCrawlerLanguageEntity> {

	public OpenHubCrawlerLanguageEntity findCrawlerConfig();
	
}
