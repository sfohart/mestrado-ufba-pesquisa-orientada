package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OhLohCrawlerStackEntity;

public interface OhLohCrawlerStackService extends BaseOhLohService<Long, OhLohCrawlerStackEntity> {

	public OhLohCrawlerStackEntity findCrawlerConfig();
	
}
