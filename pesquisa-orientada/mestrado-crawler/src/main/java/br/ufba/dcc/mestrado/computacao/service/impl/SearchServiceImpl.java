package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetSelection;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;

@Service(SearchServiceImpl.BEAN_NAME)
public class SearchServiceImpl implements SearchService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3064132003293997500L;
	
	public static final String BEAN_NAME =  "searchService";
	
	@Autowired
	private OhLohProjectRepository projectRepository;
	
	public class SearchResponse {
		
		private Integer totalResults;
		
		private List<Facet> tagFacetsList;
		private List<OhLohProjectEntity> projectList;
		
		private SearchResponse(
				List<Facet> tagFacetsList,
				List<OhLohProjectEntity> projectList,
				Integer totalResults) {
			super();
			this.tagFacetsList = tagFacetsList;
			this.projectList = projectList;
			this.totalResults = totalResults;
		}
		
		public List<Facet> getTagFacetsList() {
			return tagFacetsList;
		}
		
		public List<OhLohProjectEntity> getProjectList() {
			return projectList;
		}
		
		public Integer getTotalResults() {
			return totalResults;
		}
	}
	
	@Transactional(readOnly = true)
	public SearchResponse findAllProjects(String query) {
		return findAllProjects(query, null, null);
	}
	
	@Transactional(readOnly = true)
	public SearchResponse findAllProjects(String query, Integer startPosition, Integer maxResult) {
		return findAllProjects(query, startPosition, maxResult, (List<Facet>) null);
	}
	
	@Transactional(readOnly = true)
	public SearchResponse findAllProjects(
			String query, 
			Integer startPosition, 
			Integer maxResult,
			List<Facet> selectedFacets) {
		return findAllProjects(query, startPosition, maxResult, selectedFacets, null);
	}
	
	@Transactional(readOnly = true)
	public SearchResponse findAllProjects(
			String query, 
			Integer startPosition, 
			Integer maxResult,
			List<Facet> selectedFacets,
			List<Facet> desselectedFacets) {
		FullTextQuery fullTextQuery = projectRepository.findAllByFullTextQuery(query);
		
		FacetManager facetManager = fullTextQuery.getFacetManager();
		
		FacetingRequest facetingRequest = projectRepository.createTagFacetingRequest();
		facetManager.enableFaceting(facetingRequest);
		
		List<Facet> tagFacets = facetManager.getFacets(facetingRequest.getFacetingName());
		
		if (startPosition != null) {
			fullTextQuery.setFirstResult(startPosition);
		}
		
		if (maxResult != null) {
			fullTextQuery.setMaxResults(maxResult);
		}
		
		if (selectedFacets != null && ! selectedFacets.isEmpty()) {
			FacetSelection facetSelection = facetManager.getFacetGroup(facetingRequest.getFacetingName());
			facetSelection.selectFacets((Facet[]) selectedFacets.toArray());
		}
		
		if (desselectedFacets != null && ! desselectedFacets.isEmpty()) {
			FacetSelection facetSelection = facetManager.getFacetGroup(facetingRequest.getFacetingName());
			facetSelection.deselectFacets((Facet[]) desselectedFacets.toArray());
		}
				
		Integer totalResults = fullTextQuery.getResultSize();
		
		List<OhLohProjectEntity> projectList = fullTextQuery.getResultList();		
		
		SearchResponse searchResult = new SearchResponse(tagFacets, projectList, totalResults);
		return searchResult;
	}

	
}
