package br.ufba.dcc.mestrado.computacao.repository.base;

import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.facet.FacetingRequest;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

public interface OhLohProjectRepository extends BaseRepository<Long, OhLohProjectEntity>{

	OhLohProjectEntity findByName(String name);
	
	FullTextQuery findAllByFullTextQuery(String query);
	
	FacetingRequest createTagFacetingRequest();
}
