package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohCrawlerLanguageEntity;

public interface OhLohCrawlerLanguageService extends BaseOhLohService<Long, OhLohCrawlerLanguageEntity> {

	public OhLohCrawlerLanguageEntity findCrawlerConfig();
	
}