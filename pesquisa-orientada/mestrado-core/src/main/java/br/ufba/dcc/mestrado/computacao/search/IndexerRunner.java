package br.ufba.dcc.mestrado.computacao.search;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.service.base.IndexBuilderService;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.spring.CoreAppConfig;

@Component
public class IndexerRunner {
	
	private final static Logger logger = Logger.getLogger(IndexerRunner.class.getName());

	@Autowired
	private IndexBuilderService indexBuilderService;

	@Autowired
	private SearchService searchService;

	public void run() {
		logger.info("Iniciando (re)criação dos índices do hibernate/lucene");
		
		try {
			Instant startAt = Instant.now();			
			indexBuilderService.buildIndex();
			Instant endAt = Instant.now();
			
			Duration duration = new Duration(startAt, endAt);
			
			LocalDateTime localDateTime = new LocalDateTime(duration.getMillis());
			
			logger.info("Tempo de duração: " + localDateTime.toString());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				CoreAppConfig.class);

		IndexerRunner main = context.getBean(IndexerRunner.class);

		if (main != null) {
			main.run();
		}
	}

}
