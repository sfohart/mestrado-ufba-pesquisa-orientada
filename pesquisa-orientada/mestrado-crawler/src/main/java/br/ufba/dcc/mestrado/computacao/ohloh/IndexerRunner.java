package br.ufba.dcc.mestrado.computacao.ohloh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import br.ufba.dcc.mestrado.computacao.service.base.Indexer;
import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;

public class IndexerRunner {
	
	@Autowired
	private Indexer indexer;
	
	public void run() {
		try {
			indexer.buildIndex();
		} catch (InterruptedException e) {
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
		
		((AbstractApplicationContext) context).registerShutdownHook();
	}

}
