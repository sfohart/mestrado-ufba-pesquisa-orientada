package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.search.query.facet.Facet;

import br.ufba.dcc.mestrado.computacao.search.SearchResponse;

public interface SearchService extends Serializable {

	SearchResponse findAllProjects(String query);
	
	SearchResponse findAllProjects(String query, Integer startPosition, Integer maxResult);
	
	SearchResponse findAllProjects(String query, Integer startPosition, Integer maxResult, List<Facet> selectedFacets);
	
	SearchResponse findAllProjects(String query, Integer startPosition, Integer maxResult, List<Facet> selectedFacets, List<Facet> desselectedFacets);
	
}
