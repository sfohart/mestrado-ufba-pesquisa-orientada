package br.com.ufba.mestrado.computacao.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.service.impl.SearchServiceImpl.SearchResponse;

@ManagedBean(name="mainMB")
@ViewScoped
public class MainManageBean {
	
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
	
	public String searchProjects() {
		this.searchResponse = searchService.findAllProjects(getSearchRequest().getQuery());
		
		return "results?faces-redirect=true";
	}
	
	
	
}
