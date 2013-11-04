package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3064132003293997500L;
	@Autowired
	private OhLohProjectRepository projectRepository;
	
	
	public class SearchResult {
		private List<Facet> tagFacetsList;
		private List<OhLohProjectEntity> projectList;
		
		private SearchResult(List<Facet> tagFacetsList,
				List<OhLohProjectEntity> projectList) {
			super();
			this.tagFacetsList = tagFacetsList;
			this.projectList = projectList;
		}
		
		public List<Facet> getTagFacetsList() {
			return tagFacetsList;
		}
		
		public List<OhLohProjectEntity> getProjectList() {
			return projectList;
		}
	}
	
	@Transactional(readOnly = true)
	public SearchResult findAllProjects(String query) {
		FullTextQuery fullTextQuery = projectRepository.findAllByFullTextQuery(query);
		
		FacetManager facetManager = fullTextQuery.getFacetManager();
		
		FacetingRequest facetingRequest = projectRepository.createTagFacetingRequest();
		facetManager.enableFaceting(facetingRequest);
		
		List<Facet> tagFacets = facetManager.getFacets(facetingRequest.getFacetingName());
		List<OhLohProjectEntity> projectList = fullTextQuery.getResultList();
		
		SearchResult searchResult = new SearchResult(tagFacets, projectList);
		return searchResult;
	}

	
}
