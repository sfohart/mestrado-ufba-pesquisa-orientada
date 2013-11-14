package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.CacheMode;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.hibernate.search.impl.SimpleIndexingProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.Indexer;

@Component(HibernateSearchIndexer.BEAN_NAME)
public class HibernateSearchIndexer implements Indexer {
	
	public static final String BEAN_NAME = "hibernateSearchIndexer";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void buildIndex() throws InterruptedException, ExecutionException {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		MassIndexer massIndexer = fullTextEntityManager.createIndexer(
				OhLohProjectEntity.class
				);
		
		
		MassIndexerProgressMonitor monitor = new SimpleIndexingProgressMonitor();
		
		massIndexer.batchSizeToLoadObjects( 25 )
			.cacheMode( CacheMode.NORMAL )
			.threadsToLoadObjects( 5 )
			.idFetchSize( 150 )
			.threadsForSubsequentFetching( 50 )
			.progressMonitor( monitor ); //a MassIndexerProgressMonitor implementation
		 
		massIndexer.optimizeOnFinish(true);

		/*Future<?> future = massIndexer.start();
		while (! future.isDone()) {
			Thread.sleep(10000);
		}
		
		System.out.println("Canceling indexer thread: " + future.cancel(true));*/
		
		massIndexer.startAndWait();
		
		getEntityManager().close();
	}

}
