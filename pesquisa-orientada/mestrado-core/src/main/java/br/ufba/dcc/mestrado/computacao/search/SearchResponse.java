package br.ufba.dcc.mestrado.computacao.search;

import java.io.Serializable;
import java.util.List;

import org.hibernate.search.query.facet.Facet;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

public class SearchResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2520032483584288157L;

	private Integer totalResults;
	
	private List<Facet> tagFacetsList;
	private List<OpenHubProjectEntity> projectList;
	
	public SearchResponse(
			List<Facet> tagFacetsList,
			List<OpenHubProjectEntity> projectList,
			Integer totalResults) {
		super();
		this.tagFacetsList = tagFacetsList;
		this.projectList = projectList;
		this.totalResults = totalResults;
	}
	
	public List<Facet> getTagFacetsList() {
		return tagFacetsList;
	}
	
	public List<OpenHubProjectEntity> getProjectList() {
		return projectList;
	}
	
	public Integer getTotalResults() {
		return totalResults;
	}
}