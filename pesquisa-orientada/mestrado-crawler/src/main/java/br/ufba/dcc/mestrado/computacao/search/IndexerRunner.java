package br.ufba.dcc.mestrado.computacao.search;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.service.base.Indexer;
import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;

public class IndexerRunner {
	
	@Autowired
	private Indexer indexer;
	
	@Autowired
	private OhLohProjectRepository repository;
	
	public void run() {
		try {
			
			List<OhLohProjectEntity> resultList = repository.findAllByFullTextQuery("pdf");
			
			indexer.buildIndex();
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
