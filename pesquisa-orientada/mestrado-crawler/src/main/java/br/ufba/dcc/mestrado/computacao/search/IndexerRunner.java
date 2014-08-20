package br.ufba.dcc.mestrado.computacao.search;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.LocalTime;
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

	public void run() {
		logger.info("Iniciando (re)criação dos índices do hibernate/lucene");
		
		try {
			
			Instant startAt = Instant.now();
			indexer.buildIndex();
			Instant endAt = Instant.now();
			
			Duration duration = new Duration(startAt, endAt);
			
			LocalTime localTime = new LocalTime(duration.getMillis());
			
			logger.info(String.format("Tempo de duração: %s", localTime.toString()));
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				CrawlerAppConfig.class);
		
		((AnnotationConfigApplicationContext) context).close();

		IndexerRunner main = context.getBean(IndexerRunner.class);

		if (main != null) {
			main.run();
			System.exit(0);
		}
	}

}
