package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.search.query.facet.Facet;

import br.ufba.dcc.mestrado.computacao.service.impl.SearchServiceImpl.SearchResult;

public interface SearchService extends Serializable {

	SearchResult findAllProjects(String query);
	
	SearchResult findAllProjects(String query, Integer startPosition, Integer maxResult);
	
	SearchResult findAllProjects(String query, Integer startPosition, Integer maxResult, List<Facet> selectedFacets);
	
	SearchResult findAllProjects(String query, Integer startPosition, Integer maxResult, List<Facet> selectedFacets, List<Facet> desselectedFacets);
	
}
