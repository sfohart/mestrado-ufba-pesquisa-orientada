package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.crawler.OhLohCrawlerLanguageEntity;

public interface OhLohCrawlerLanguageRepository extends BaseRepository<Long, OhLohCrawlerLanguageEntity>{
	
	public OhLohCrawlerLanguageEntity findCrawlerConfig();
	
}
