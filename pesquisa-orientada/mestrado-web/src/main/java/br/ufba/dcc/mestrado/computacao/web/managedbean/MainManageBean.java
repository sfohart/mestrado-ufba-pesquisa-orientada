package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.hibernate.search.query.facet.Facet;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.search.SearchRequest;
import br.ufba.dcc.mestrado.computacao.search.SearchResponse;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.web.pagination.LazyLoadingDataModel;
import br.ufba.dcc.mestrado.computacao.web.pagination.PageList;

/**
 * 
 * @author leandro.ferreira
 *
 */
@ManagedBean(name="mainMB")
@ViewScoped
public class MainManageBean extends AbstractListingManagedBean<Long, OpenHubProjectEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6637959023617248522L;

	@ManagedProperty("#{searchService}")
	private SearchService searchService;
	
	@ManagedProperty("#{projectService}")
	private ProjectService projectService;

	private SearchRequest searchRequest;
	
	public MainManageBean() {
		this.searchRequest = new SearchRequest();	
	}
	
	public String loadResults() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("/search/results.jsf?faces-redirect=true&query=" + getSearchRequest().getQuery());
		
		return buffer.toString();
	}
	
	public SearchService getSearchService() {
		return searchService;
	}
	
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	public ProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public SearchRequest getSearchRequest() {
		return searchRequest;
	}
	
	public void setSearchRequest(SearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}
	
	@Override
	protected OpenHubProjectEntity findSelectedEntityById(Long id) {
		return getProjectService().findById(id);
	}
	
	public void init(ComponentSystemEvent event) {
		if (getSearchRequest() != null 
				&& getSearchRequest().getQuery() != null
				&& ! "".equals(getSearchRequest().getQuery())) {
			
			this.dataModel = new LazyLoadingDataModel<Long, OpenHubProjectEntity>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 6859894771619835765L;

				@Override
				public void load(int startPosition, int maxResult) {
					
					getSearchRequest().setStartPosition(startPosition);
					getSearchRequest().setMaxResult(maxResult);
					
					SearchResponse searchResponse = getSearchService().findAllProjects(getSearchRequest());
					
					PageList pageList = null;
					
					if (searchResponse != null) {
						List<OpenHubProjectEntity> data = searchResponse.getProjectList();
						this.setWrappedData(data);
						
						getSearchRequest().getDeselectedFacets().clear();
						getSearchRequest().getDeselectedFacets().addAll(searchResponse.getTagFacetsList());
						getSearchRequest().getDeselectedFacets().removeAll(getSearchRequest().getSelectedFacets());
						
						this.setRowCount(data.size());
						
						Integer currentPage = (startPosition / maxResult) + 1;
						
						pageList = new PageList(currentPage, searchResponse.getTotalResults(), maxResult);
					} else {
						pageList = new PageList();
					}
					
					setPageList(pageList);
				}
			};
			
			this.dataModel.setPageSize(10);
			
			setDataModel(dataModel);
			
			
			Integer startPosition = loadStartPositionFromParams();
			Integer pageSize = getDataModel().getPageSize();
			
			getDataModel().load(startPosition, pageSize);
		}
	}
	
	public void searchProjects(ActionEvent event) {
		Integer startPosition = loadStartPositionFromParams();
		Integer pageSize = getDataModel().getPageSize();
		
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
	
	public String compareProjects() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("/compare/compareProjects.jsf?faces-redirect=true");
		
		for (Map.Entry<Long, Boolean> entry : getSelectedItems().entrySet()) {
			if (entry.getValue()) {
				buffer.append("&projectId=" + entry.getKey());
			}
		}
		
		return buffer.toString();
	}
	
}
