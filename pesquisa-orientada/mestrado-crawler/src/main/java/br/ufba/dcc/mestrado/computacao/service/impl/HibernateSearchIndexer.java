package br.ufba.dcc.mestrado.computacao.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.CacheMode;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.hibernate.search.jmx.IndexingProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.Indexer;

public class HibernateSearchIndexer implements Indexer {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void buildIndex() throws InterruptedException {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		MassIndexer massIndexer = fullTextEntityManager.createIndexer(
				OhLohProjectEntity.class
				);
		
		
		MassIndexerProgressMonitor monitor = new IndexingProgressMonitor();
		
		massIndexer.batchSizeToLoadObjects( 25 )
		 .cacheMode( CacheMode.NORMAL )
		 .threadsToLoadObjects( 5 )
		 .idFetchSize( 150 )
		 .threadsForSubsequentFetching( 20 )
		 .progressMonitor( monitor ) //a MassIndexerProgressMonitor implementation
		 .startAndWait();

	}

}
