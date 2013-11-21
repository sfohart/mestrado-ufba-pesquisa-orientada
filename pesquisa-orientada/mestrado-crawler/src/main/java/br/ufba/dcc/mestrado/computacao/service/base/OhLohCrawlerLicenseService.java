package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerLicenseEntity;

public interface OhLohCrawlerLicenseService extends BaseOhLohService<Long, OhLohCrawlerLicenseEntity> {

	public OhLohCrawlerLicenseEntity findCrawlerConfig();
	
}
