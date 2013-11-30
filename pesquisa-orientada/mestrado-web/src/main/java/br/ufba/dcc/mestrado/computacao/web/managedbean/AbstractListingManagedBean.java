package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.web.pagination.LazyLoadingDataModel;
import br.ufba.dcc.mestrado.computacao.web.pagination.PageList;


public abstract class AbstractListingManagedBean<ID extends Number, E extends BaseEntity<ID>> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916236534323362457L;

	public LazyLoadingDataModel<ID,E> dataModel;
	
	private PageList pageList;
	
	public PageList getPageList() {
		return pageList;
	}
	
	public void setPageList(PageList pageList) {
		this.pageList = pageList;
	}
	
	public LazyLoadingDataModel<ID, E> getDataModel() {
		return dataModel;
	}
	
	public void setDataModel(LazyLoadingDataModel<ID, E> dataModel) {
		this.dataModel = dataModel;
	}
	
	protected Integer loadStartPositionFromParams() {
		Map<String, String> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
		
		String pageParam = params.get("page");
		
		Integer startPosition = null;
		
		if (pageParam != null){
			Integer page = Integer.parseInt(pageParam);
			if (page != null && page > 0) {
				startPosition = getDataModel().getPageSize() * (page - 1);
			}
		} else {
			startPosition = 0;
		}
		
		return startPosition;
	}
	
}
