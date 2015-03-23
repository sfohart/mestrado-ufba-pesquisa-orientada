package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.facet.FacetingRequest;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;

public interface ProjectRepository extends BaseRepository<Long, OpenHubProjectEntity>{

	OpenHubProjectEntity findByName(String name);
	
	FullTextQuery findAllByFullTextQuery(String query);
	
	FacetingRequest createTagFacetingRequest();

	FullTextQuery findRelatedProjects(OpenHubProjectEntity project) throws IOException;

	IndexReader getIndexReader();
}
