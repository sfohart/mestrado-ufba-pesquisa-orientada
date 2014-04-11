package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerStackEntity;

public interface OhLohCrawlerStackService extends BaseOhLohService<Long, OhLohCrawlerStackEntity> {

	public OhLohCrawlerStackEntity findCrawlerConfig();
	
}
