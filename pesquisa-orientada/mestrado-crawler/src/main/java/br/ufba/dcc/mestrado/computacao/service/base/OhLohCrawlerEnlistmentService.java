package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.ohloh.entities.OhLohCrawlerEnlistmentEntity;

public interface OhLohCrawlerEnlistmentService extends BaseOhLohService<Long, OhLohCrawlerEnlistmentEntity> {

	public OhLohCrawlerEnlistmentEntity findCrawlerConfig();
	
}
