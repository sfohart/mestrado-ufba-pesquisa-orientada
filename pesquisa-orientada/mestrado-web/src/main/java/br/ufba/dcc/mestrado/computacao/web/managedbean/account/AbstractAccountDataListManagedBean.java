package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

public abstract class AbstractAccountDataListManagedBean<ID extends Number, T extends BaseEntity<ID>> extends AbstractAccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4595879662990475605L;
	
	private Integer startPosition;
	private Integer offset;
	private Long total;
	
	private List<T> dataList;
	
	public Integer getStartPosition() {
		return this.startPosition;
	}
	
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	
	public Integer getOffset() {
		return this.offset;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public List<T> getDataList() {
		return dataList;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	protected abstract List<T> searchDataList(Integer startPosition, Integer offset);	
	protected abstract Long countDataList(); 
	
	protected void searchDataList() {
		
		List<T> data = searchDataList(startPosition, offset);
		total = countDataList();
		
		if (data != null) {
			for (T datum : data) {
				if (getDataList().contains(datum)) {
					getDataList().remove(datum);
				}
				
				getDataList().add(datum);
			}
		}
	}
	
	public void moreData(ActionEvent event) {
		if (getDataList() != null) {
			startPosition = getDataList().size();
		}
	
		searchDataList();
	}
	
	public void searchReviews(ActionEvent event) {
		searchDataList();
	}
	
	public void initList(ComponentSystemEvent event) {		
		if (getAccount() != null && getAccount().getId() != null) {
			super.init(event);
			
			if (getDataList() == null) {
				setDataList(new ArrayList<T>());
				this.startPosition = 0;
				this.offset = 9;
				this.total = 0L;
			
				searchDataList();
			}
		}
	}
}
