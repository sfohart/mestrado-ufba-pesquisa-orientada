package br.ufba.dcc.mestrado.computacao.search;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufba.dcc.mestrado.computacao.service.base.Indexer;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;

public class IndexerRunner {
	
	private static Logger logger = Logger.getLogger(IndexerRunner.class);
	
	@Autowired
	private Indexer indexer;
	
	@Autowired
	private SearchService searchService;
	
	public void run() {
		try {
			indexer.buildIndex();
			
			SearchResponse searchResult = searchService.findAllProjects("pdf");
			
			logger.info(String.format("%d Hits, %d facets", searchResult.getTotalResults(), searchResult.getTagFacetsList().size() ));
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context = 
		          new AnnotationConfigApplicationContext(CrawlerAppConfig.class);
		
		IndexerRunner main = context.getBean(IndexerRunner.class);
		
		if (main != null) {
			main.run();
		}
	}

}
