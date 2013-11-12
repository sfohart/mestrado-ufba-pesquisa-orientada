package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.hibernate.search.query.facet.Facet;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.search.SearchResponse;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.web.pagination.LazyLoadingDataModel;
import br.ufba.dcc.mestrado.computacao.web.pagination.PageList;

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
	
	private LazyLoadingDataModel<Long, OhLohProjectEntity> dataModel;
	private PageList pageList;
	
	public MainManageBean() {
		this.searchRequest = new SearchRequest();
		
		this.dataModel = new LazyLoadingDataModel<Long, OhLohProjectEntity>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6859894771619835765L;

			@Override
			public void load(int startPosition, int maxResult) {
				
				getSearchRequest().setStartPosition(startPosition);
				getSearchRequest().setMaxResult(maxResult);
				
				SearchResponse searchResponse = getSearchService().findAllProjects(getSearchRequest());
				
				List<OhLohProjectEntity> data = searchResponse.getProjectList();
				this.setWrappedData(data);
				
				getSearchRequest().getDeselectedFacets().clear();
				getSearchRequest().getDeselectedFacets().addAll(searchResponse.getTagFacetsList());
				getSearchRequest().getDeselectedFacets().removeAll(getSearchRequest().getSelectedFacets());
				
				this.setRowCount(data.size());
				
				Integer currentPage = (startPosition / maxResult) + 1;
				
				PageList pageList = new PageList(currentPage, searchResponse.getTotalResults(), maxResult);
				setPageList(pageList);
			}
		};
		
		this.dataModel.setPageSize(10);
	}
	
	
	public String loadResults() {
		return "results.jsf?faces-redirect=true&query=" + getSearchRequest().getQuery();
	}
	
	public LazyLoadingDataModel<Long, OhLohProjectEntity> getDataModel() {
		return dataModel;
	}
	
	public SearchService getSearchService() {
		return searchService;
	}
	
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	public SearchRequest getSearchRequest() {
		return searchRequest;
	}
	
	public void setSearchRequest(SearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}
	
	public PageList getPageList() {
		return pageList;
	}
	
	public void setPageList(PageList pageList) {
		this.pageList = pageList;
	}
	
	public void searchProjects(ActionEvent event) {
		Map<String, String> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
		
		String pageParam = params.get("page");
		
		Integer startPosition = null;
		Integer pageSize = getDataModel().getPageSize();
		
		if (pageParam != null){
			Integer page = Integer.parseInt(pageParam);
			if (page != null && page > 0) {
				startPosition = getDataModel().getPageSize() * (page - 1);
			}
		} else {
			startPosition = 0;
		}
		
		getSearchRequest().setStartPosition(startPosition);
		
		getDataModel().load(startPosition, pageSize);
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
		
		if (getSearchRequest() != null && getSearchRequest().getDeselectedFacets() != null) {
			Facet selectedFacet = null;
			for (Facet facet : getSearchRequest().getDeselectedFacets()) {
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
