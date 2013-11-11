package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.search.query.facet.Facet;

import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.search.SearchResponse;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;

@ManagedBean(name="mainMB")
@ViewScoped
public class MainManageBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2856136549457564028L;

	@ManagedProperty("#{searchService}")
	private SearchService searchService;

	private SearchRequest searchRequest;
	private SearchResponse searchResponse;
	
	public SearchService getSearchService() {
		return searchService;
	}
	
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	public MainManageBean() {
		this.searchRequest = new SearchRequest();
	}
	
	public SearchRequest getSearchRequest() {
		return searchRequest;
	}
	
	public void setSearchRequest(SearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}
	
	public SearchResponse getSearchResponse() {
		return searchResponse;
	}
	
	public void searchProjects(ActionEvent event) {
		Map<String, String> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
		
		String pageParam = params.get("page");
		
		Integer startPosition = null;
		
		if ("first".equals(pageParam)) {
			startPosition = 0;
		} else if ("last".equals(pageParam)) {
			Integer page = getSearchResponse().getTotalResults() / getSearchRequest().getMaxResult();
			startPosition = getSearchRequest().getMaxResult() * page;
		} else if (pageParam != null){
			Integer page = Integer.parseInt(pageParam);
			if (page != null && page > 0) {
				startPosition = getSearchRequest().getMaxResult() * page;
			}
		}
		
		getSearchRequest().setStartPosition(startPosition);
		
		this.searchResponse = getSearchService().findAllProjects(getSearchRequest());
		
		
		getSearchRequest().getDeselectedFacets().addAll(getSearchResponse().getTagFacetsList());
		getSearchRequest().getDeselectedFacets().removeAll(getSearchRequest().getSelectedFacets());
	}
	
	public void removeFacetsFromFilter(ActionEvent event) {
		Map<String, String> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
		
		String facetValue = params.get("facetValue");
		
		if (getSearchRequest() != null && getSearchRequest().getSelectedFacets() != null) {
			Facet deselectedFacet = null;
			for (Facet facet : getSearchRequest().getSelectedFacets()) {
				if (facet.getValue().equals(facetValue)) {
					deselectedFacet = facet;
					break;
				}
			}
			
			if (deselectedFacet != null) {
				if (! getSearchRequest().getDeselectedFacets().contains(deselectedFacet)) {
					getSearchRequest().getDeselectedFacets().add(deselectedFacet);
				}
				
				getSearchRequest().getSelectedFacets().remove(deselectedFacet);
			}
		}
		
		searchProjects(event);
	}

	public void addFacetsToFilter(ActionEvent event) {
		Map<String, String> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
		
		String facetValue = params.get("facetValue");
		
		if (getSearchResponse() != null && getSearchResponse().getTagFacetsList() != null) {
			Facet selectedFacet = null;
			for (Facet facet : getSearchResponse().getTagFacetsList()) {
				if (facet.getValue().equals(facetValue)) {
					selectedFacet = facet;
					break;
				}
			}
			
			if (selectedFacet != null) {
				if (! getSearchRequest().getSelectedFacets().contains(selectedFacet)) {
					getSearchRequest().getSelectedFacets().add(selectedFacet);
				}
				
				getSearchRequest().getDeselectedFacets().remove(selectedFacet);
			}
			
		}
		
		searchProjects(event);
	}
	
}
