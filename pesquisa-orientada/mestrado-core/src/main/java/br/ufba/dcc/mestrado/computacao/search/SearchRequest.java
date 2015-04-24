package br.ufba.dcc.mestrado.computacao.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.query.facet.Facet;

public class SearchRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7968326126442789673L;
	
	private String query;
	private List<Facet> selectedFacets;
	private List<Facet> deselectedFacets;
	
	private Integer startPosition; 
	private Integer maxResult;
	
	public SearchRequest() {
		this.selectedFacets = new ArrayList<>();
		this.deselectedFacets = new ArrayList<>();
		this.startPosition = 0;
		this.maxResult = 10;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public List<Facet> getSelectedFacets() {
		return selectedFacets;
	}
	
	public void setSelectedFacets(List<Facet> selectedFacets) {
		this.selectedFacets = selectedFacets;
	}
	
	public List<Facet> getDeselectedFacets() {
		return deselectedFacets;
	}
	
	public void setDeselectedFacets(List<Facet> deselectedFacets) {
		this.deselectedFacets = deselectedFacets;
	}

	public Integer getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}

	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}
	
}