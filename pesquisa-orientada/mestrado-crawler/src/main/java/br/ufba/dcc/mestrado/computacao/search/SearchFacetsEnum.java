package br.ufba.dcc.mestrado.computacao.search;

public enum SearchFacetsEnum {
	tagFacet("ohLohTags.tag_facet");
	
	private String facetName;
	
	private SearchFacetsEnum(String facetName) {
		this.facetName = facetName;
	}
	
	public String facetName() {
		return this.facetName;
	}
}
