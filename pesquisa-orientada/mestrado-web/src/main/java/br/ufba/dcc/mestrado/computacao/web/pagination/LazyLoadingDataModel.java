package br.ufba.dcc.mestrado.computacao.web.pagination;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

public abstract class LazyLoadingDataModel<ID extends Number, E extends BaseEntity<ID>> extends ListDataModel<E> 
	implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4177983451326119885L;
	
	private int rowCount;
	private int pageSize;

	@Override
	@SuppressWarnings("unchecked")
	public boolean isRowAvailable() {
		List<E> data = (List<E>) getWrappedData(); 
		if(data == null) {
            return false;
        }

		return getRowIndex() >= 0 && getRowIndex() < data.size();
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}
	
	public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

	@Override
	@SuppressWarnings("unchecked")
	public E getRowData() {
		List<E> data = (List<E>) getWrappedData();
		
		if (isRowAvailable()) {
			return data.get(getRowIndex());
		}
		
		return null;
	}
	
	public int getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public abstract void load(int first, int pageSize);

}
