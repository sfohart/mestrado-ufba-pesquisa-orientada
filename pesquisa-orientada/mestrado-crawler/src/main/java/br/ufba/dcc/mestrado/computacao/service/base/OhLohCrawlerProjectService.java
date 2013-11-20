package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerProjectEntity;

public interface OhLohCrawlerProjectService extends BaseOhLohService<Long, OhLohCrawlerProjectEntity> {

	public OhLohCrawlerProjectEntity findCrawlerConfig();
}
