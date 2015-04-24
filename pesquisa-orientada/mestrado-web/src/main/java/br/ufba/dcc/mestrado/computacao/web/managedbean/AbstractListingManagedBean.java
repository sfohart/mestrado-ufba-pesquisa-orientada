package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.web.pagination.LazyLoadingDataModel;
import br.ufba.dcc.mestrado.computacao.web.pagination.PageList;

/**
 * 
 * @author leandro.ferreira
 *
 * @param <ID>
 * @param <E>
 */
public abstract class AbstractListingManagedBean<ID extends Number, E extends BaseEntity<ID>> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916236534323362457L;

	public LazyLoadingDataModel<ID,E> dataModel;
	
	private PageList pageList = new PageList();
	
	private Map<ID, Boolean> selectedItems = new HashMap<ID, Boolean>();
	
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
		} else if (getPageList().getCurrentPage() != null && getPageList().getCurrentPage() > 0) {
			startPosition = getDataModel().getPageSize() * (getPageList().getCurrentPage() - 1);
		} else {
			startPosition = 0;
		}
		
		return startPosition;
	}
	
	@SuppressWarnings("unchecked")
	public void onChangeSelectedItem(ValueChangeEvent event) {
		Integer currentPage = (Integer) event.getComponent().getAttributes().get("currentPage");
		getPageList().setCurrentPage(currentPage);
		
		
		ID entityId = (ID) event.getComponent().getAttributes().get("entityId");
		getSelectedItems().put(entityId, (Boolean) event.getNewValue());
	}
	
	protected void postRemoveSelectedItem(ActionEvent event) {
		
	}
	
	@SuppressWarnings("unchecked")
	public void removeSelectedItem(ActionEvent event) {
		ID selectedId = (ID) event.getComponent().getAttributes().get("selectedId");
		getSelectedItems().put(selectedId, Boolean.FALSE);
		
		postRemoveSelectedItem(event);
	}
	
	public Map<ID, Boolean> getSelectedItems() {
		return selectedItems;
	}
	
	public void setSelectedItems(Map<ID, Boolean> selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	protected E findSelectedEntityById(ID id) {
		return null;
	}
	
	public List<E> getSelectedEntities() {
		List<E> list = new ArrayList<>();
		
		for (Map.Entry<ID, Boolean> entry : selectedItems.entrySet()) {
			if (entry.getValue()) {
				E entity = findSelectedEntityById(entry.getKey());
				if (entity != null) {
					list.add(entity);
				}
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
