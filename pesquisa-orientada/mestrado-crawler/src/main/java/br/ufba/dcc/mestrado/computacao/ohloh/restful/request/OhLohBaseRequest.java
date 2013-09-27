package br.ufba.dcc.mestrado.computacao.ohloh.restful.request;

/**
 * 
 * @author leandro
 *
 */
public class OhLohBaseRequest {
	public String query;
	public String sort;
	public Integer page;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
