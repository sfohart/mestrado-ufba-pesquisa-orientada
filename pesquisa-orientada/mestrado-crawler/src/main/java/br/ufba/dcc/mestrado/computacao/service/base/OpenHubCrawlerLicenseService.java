package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerLicenseEntity;

public interface OpenHubCrawlerLicenseService extends BaseOpenHubService<Long, OpenHubCrawlerLicenseEntity> {

	public OpenHubCrawlerLicenseEntity findCrawlerConfig();
	
}
