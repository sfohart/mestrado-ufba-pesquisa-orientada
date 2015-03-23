package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OpenHubCrawlerProjectEntity;

public interface OpenHubCrawlerProjectService extends BaseOpenHubService<Long, OpenHubCrawlerProjectEntity> {

	public OpenHubCrawlerProjectEntity findCrawlerConfig();
}
