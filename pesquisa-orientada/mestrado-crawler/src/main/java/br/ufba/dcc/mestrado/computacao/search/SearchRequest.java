package br.ufba.dcc.mestrado.computacao.search;

import java.util.List;

import org.hibernate.search.query.facet.Facet;

public class SearchRequest {
	private String query;
	private List<Facet> selectedFacets;
	private List<Facet> deselectedFacets;
	
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
}