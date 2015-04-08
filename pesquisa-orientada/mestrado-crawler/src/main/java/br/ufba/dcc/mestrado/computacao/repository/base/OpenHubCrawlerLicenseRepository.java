package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.openhub.crawler.OpenHubCrawlerLicenseEntity;

public interface OpenHubCrawlerLicenseRepository extends BaseRepository<Long, OpenHubCrawlerLicenseEntity>{
	
	public OpenHubCrawlerLicenseEntity findCrawlerConfig();
	
}
