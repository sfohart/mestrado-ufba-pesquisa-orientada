package br.ufba.dcc.mestrado.computacao.search;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.service.base.Indexer;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.spring.CrawlerAppConfig;

@Component
public class IndexerRunner {
	
	private final static Logger logger = Logger.getLogger(IndexerRunner.class.getName());

	@Autowired
	private Indexer indexer;

	@Autowired
	private SearchService searchService;

	/**
	 * Inicia a (re)constru��o dos �ndices do Apache Lucene, para posterior pesquisa com o
	 * Hibernate Search. A anota��o {@link Scheduled} indica, atrav�s de uma express�o cron-like
	 * ( @see http://en.wikipedia.org/wiki/Cron ) o per�odo de agendamento da execu��o.
	 * 
	 * Não usar Jobs aqui, openshift pode dar OutOfMemoryException
	 *  
	 */	
	public void run() {
		Timestamp duration = null;
		logger.info("Iniciando recria��o dos �ndices do hibernate/lucene");
		
		try {
			long startAt = System.currentTimeMillis();
			indexer.buildIndex();
			long endAt = System.currentTimeMillis();
			
			duration = new Timestamp(endAt - startAt);
			
			SimpleDateFormat format = new SimpleDateFormat("mm:ss.S");
			
			logger.info("Tempo de dura��o: " + format.format(duration));
			
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				CrawlerAppConfig.class);

		IndexerRunner main = context.getBean(IndexerRunner.class);

		if (main != null) {
			main.run();
		}
	}

}
