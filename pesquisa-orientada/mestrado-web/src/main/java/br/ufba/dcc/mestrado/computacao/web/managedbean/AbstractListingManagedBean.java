package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.service.base.BaseOhLohService;
import br.ufba.dcc.mestrado.computacao.web.pagination.LazyLoadingDataModel;
import br.ufba.dcc.mestrado.computacao.web.pagination.PageList;


public abstract class AbstractListingManagedBean<ID extends Number, E extends BaseEntity<ID>> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916236534323362457L;

	public LazyLoadingDataModel<ID,E> dataModel;
	
	private PageList pageList = new PageList();
	
	private Map<ID, Boolean> selectedItems = new HashMap<ID, Boolean>();
	
	@Autowired
	private BaseOhLohService<ID, E> service;
	
	public BaseOhLohService<ID, E> getService() {
		return service;
	}
	
	public void setService(BaseOhLohService<ID, E> service) {
		this.service = service;
	}
	
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
	
	protected Integer loadPageNumberParam() {
		Map<String, String> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
		
		String pageParam = params.get("page");
		Integer page = null;
		
		if (pageParam !=  null) {
			page = Integer.parseInt(pageParam);
		} 
		
		return page;
	}
	
	protected Integer loadStartPositionFromParams() {
		Integer page = loadPageNumberParam();
		
		Integer startPosition = null;
		
		if (page != null){
			if (page != null && page > 0) {
				startPosition = getDataModel().getPageSize() * (page - 1);
			}
		} else if (getPageList().getCurrentPage() != null) {
			startPosition = getDataModel().getPageSize() * (getPageList().getCurrentPage() - 1);
		} else {
			startPosition = 0;
		}
		
		return startPosition;
	}
	
	public void onChangeSelectedItem(ValueChangeEvent event) {
		Integer currentPage = (Integer) event.getComponent().getAttributes().get("currentPage");
		getPageList().setCurrentPage(currentPage);
	}
	
	public Map<ID, Boolean> getSelectedItems() {
		return selectedItems;
	}
	
	public void setSelectedItems(Map<ID, Boolean> selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	public List<E> getSelectedEntities() {
		List<E> list = new ArrayList<>();
		
		for (Map.Entry<ID, Boolean> entry : selectedItems.entrySet()) {
			if (entry.getValue()) {
				E entity = getService().findById(entry.getKey());
				list.add(entity);
			}
		}
		
		return list;
	}
	
	public Long getSelectedEntitiesCount() {
		Long count = 0L;
		
		for (Map.Entry<ID, Boolean> entry : selectedItems.entrySet()) {
			if (entry.getValue()) {
				count++;
			}
		}
		
		return count;
	}
}
