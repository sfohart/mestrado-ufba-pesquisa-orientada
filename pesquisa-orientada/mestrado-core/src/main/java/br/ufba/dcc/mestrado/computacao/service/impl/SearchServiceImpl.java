package br.ufba.dcc.mestrado.computacao.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetSelection;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectRepository;
import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.search.SearchResponse;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;

@Service(SearchServiceImpl.BEAN_NAME)
public class SearchServiceImpl implements SearchService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3064132003293997500L;
	
	public static final String BEAN_NAME =  "searchService";
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public SearchResponse findAllProjects(SearchRequest searchRequest) {
		SearchResponse searchResult =  null;
		
		if (searchRequest != null && searchRequest.getQuery() != null) {
			FullTextQuery fullTextQuery = projectRepository.findAllByFullTextQuery(searchRequest.getQuery());
			
			if (fullTextQuery != null) {
				FacetManager facetManager = fullTextQuery.getFacetManager();
				
				FacetingRequest facetingRequest = projectRepository.createTagFacetingRequest();
				facetManager.enableFaceting(facetingRequest);
				
				List<Facet> tagFacets = facetManager.getFacets(facetingRequest.getFacetingName());
				
				Comparator<Facet> inverseComparator = new Comparator<Facet>() {
					@Override
					public int compare(Facet facet1, Facet facet2) {
						int result = 0;
						
						if (facet1.getCount() > facet2.getCount()) {
							result = -1;
						} else if (facet2.getCount() > facet1.getCount()) {
							return 1;
						}
						
						return result;
					}
				};
				
				Collections.sort(tagFacets, inverseComparator);
				
				if (searchRequest.getStartPosition() != null && searchRequest.getStartPosition() > 0) {
					fullTextQuery.setFirstResult(searchRequest.getStartPosition());
				}
				
				if (searchRequest.getMaxResult() != null && searchRequest.getMaxResult() > 0) {
					fullTextQuery.setMaxResults(searchRequest.getMaxResult());
				}
				
				if (searchRequest.getSelectedFacets() != null && ! searchRequest.getSelectedFacets().isEmpty()) {
					FacetSelection facetSelection = facetManager.getFacetGroup(facetingRequest.getFacetingName());
					facetSelection.selectFacets(searchRequest.getSelectedFacets().toArray(new Facet[0]));
				}
				
				if (searchRequest.getDeselectedFacets() != null && ! searchRequest.getDeselectedFacets().isEmpty()) {
					FacetSelection facetSelection = facetManager.getFacetGroup(facetingRequest.getFacetingName());
					facetSelection.deselectFacets(searchRequest.getDeselectedFacets().toArray(new Facet[0]));
				}
				
				Integer totalResults = fullTextQuery.getResultSize();
				
				List<OhLohProjectEntity> projectList = fullTextQuery.getResultList();		
				
				searchResult = new SearchResponse(tagFacets, projectList, totalResults);
			}
		}
		
		return searchResult;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<OhLohProjectEntity> findRelatedProjects(
			OhLohProjectEntity project,
			Integer firstResult,
			Integer maxResults) throws IOException {
		FullTextQuery fullTextQuery = projectRepository.findRelatedProjects(project);
		
		if (firstResult != null && firstResult > 0) {
			fullTextQuery.setFirstResult(firstResult);
		}
		
		if (maxResults != null && maxResults > 0) {
			fullTextQuery.setMaxResults(maxResults);
		}
		
		List<OhLohProjectEntity> resultList = fullTextQuery.getResultList();
		
		return resultList;
	}

	
}
