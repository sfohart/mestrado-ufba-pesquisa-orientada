package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OhLohCrawlerLicenseEntity;

public interface OhLohCrawlerLicenseRepository extends BaseRepository<Long, OhLohCrawlerLicenseEntity>{
	
	public OhLohCrawlerLicenseEntity findCrawlerConfig();
	
}
