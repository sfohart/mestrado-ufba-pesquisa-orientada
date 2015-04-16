package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerStackEntity;

public interface OpenHubCrawlerStackService extends BaseOpenHubService<Long, OpenHubCrawlerStackEntity> {

	public OpenHubCrawlerStackEntity findCrawlerConfig();
	
}
