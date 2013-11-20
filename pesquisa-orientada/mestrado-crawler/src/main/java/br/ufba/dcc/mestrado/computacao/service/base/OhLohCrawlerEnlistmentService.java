package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerEnlistmentEntity;

public interface OhLohCrawlerEnlistmentService extends BaseOhLohService<Long, OhLohCrawlerEnlistmentEntity> {

	public OhLohCrawlerEnlistmentEntity findCrawlerConfig();
	
}
