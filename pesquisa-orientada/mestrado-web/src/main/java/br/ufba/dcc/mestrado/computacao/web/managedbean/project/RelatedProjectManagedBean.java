package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "relatedProjectMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="relatedProjectMapping",
				beanName="relatedProjectMB", 
				pattern="/detail/#{ /[0-9]+/ projectId}/relatedProject",
				viewId="/detail/relatedProjectList.jsf")	
})
public class RelatedProjectManagedBean extends ProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -722099582447182349L;
	
	private static final Integer SAMPLE_MAX_RESULTS = 6;
	private static final Integer TOP10_MAX_RESULTS = 10;


	@ManagedProperty("#{searchService}")
	private SearchService searchService;

	private List<OhLohProjectEntity> relatedProjectEntities;

	private Integer maxResults;

	public RelatedProjectManagedBean() {
		super();
		this.maxResults = SAMPLE_MAX_RESULTS;
	}
	
	public void configureTopTenResults(ComponentSystemEvent event) {
		this.maxResults = TOP10_MAX_RESULTS;
	}

	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
			findRelatedProjectList();
		}
	}

	/**
	 * Utiliza busca por conte�do pra recomendar projetos similares.
	 */
	protected void findRelatedProjectList() {
		try {
			this.relatedProjectEntities = getSearchService().findRelatedProjects(getProject(), 0, getMaxResults());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}


	public List<OhLohProjectEntity> getRelatedProjectEntities() {
		return relatedProjectEntities;
	}

	public void setRelatedProjectEntities(
			List<OhLohProjectEntity> relatedProjectEntities) {
		this.relatedProjectEntities = relatedProjectEntities;
	}

}