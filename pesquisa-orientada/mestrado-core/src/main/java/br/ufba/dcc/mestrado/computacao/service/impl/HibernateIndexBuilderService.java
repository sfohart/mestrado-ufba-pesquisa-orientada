package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.MassIndexer;
import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.hibernate.search.batchindexing.impl.SimpleIndexingProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.IndexBuilderService;

@Component(HibernateIndexBuilderService.BEAN_NAME)
public class HibernateIndexBuilderService implements IndexBuilderService {
	
	public static final String BEAN_NAME = "hibernateIndexBuilderService";
	
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
				OpenHubProjectEntity.class
				);
		
		
		MassIndexerProgressMonitor monitor = new SimpleIndexingProgressMonitor();
		
		massIndexer
			//.batchSizeToLoadObjects( 25 )
			//.threadsToLoadObjects( 5 )
			//.idFetchSize( 150 )
			//.threadsForSubsequentFetching( 50 )
			.progressMonitor( monitor ); //a MassIndexerProgressMonitor implementation
		 
		massIndexer.optimizeOnFinish(true);

		Future<?> future = massIndexer.start();
		while (! future.isDone()) {
			Thread.sleep(10000);
		}
		
		//massIndexer.startAndWait();
		
		getEntityManager().close();
	}

}
