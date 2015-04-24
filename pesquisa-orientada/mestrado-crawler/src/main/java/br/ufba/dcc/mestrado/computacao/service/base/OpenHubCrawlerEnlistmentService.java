package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerEnlistmentEntity;

public interface OpenHubCrawlerEnlistmentService extends BaseOpenHubService<Long, OpenHubCrawlerEnlistmentEntity> {

	public OpenHubCrawlerEnlistmentEntity findCrawlerConfig();
	
}
