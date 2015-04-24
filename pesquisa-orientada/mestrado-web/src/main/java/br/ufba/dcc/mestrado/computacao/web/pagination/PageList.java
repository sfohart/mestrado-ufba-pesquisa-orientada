package br.ufba.dcc.mestrado.computacao.web.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Solução temporária.
 * 
 * @author leandro.ferreira
 *
 */
public class PageList implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6204410870261559024L;
	
	private Integer currentPage;	
	private Integer rowCount;
	private Integer pageSize;
	private Integer totalPages;
	
	private Integer maxVisiblePages;
	
	private Integer firstPage;
	private Integer lastPage;
	private Integer previousPage;
	private Integer nextPage;
	
	private List<Integer> pageList;
	
	public PageList() {
		this.currentPage = 0;
		this.rowCount = 0;
		this.pageSize = 10;
		this.totalPages = 0;
	}
	
	public PageList(Integer currentPage, Integer rowCount, Integer pageSize) {
		super();
		this.currentPage = currentPage;
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		
		this.maxVisiblePages = 10;
		this.totalPages = (rowCount / pageSize) + (rowCount % pageSize > 0 ? 1 : 0);
		
		processPageList();
	}
	
	private void processPageList() {
		if (this.pageList == null) {
			Integer pageStart = 1;
			if (currentPage % maxVisiblePages == 0) {
				pageStart = (currentPage / maxVisiblePages - 1) * maxVisiblePages + 1;
			} else {
				pageStart = (currentPage / maxVisiblePages) * maxVisiblePages + 1;
			}
			
			pageStart = pageStart < 1 ? 1 : pageStart;
			
			this.firstPage = 1;
			this.lastPage = totalPages;
			
			if (this.currentPage > 1) {
                this.previousPage = this.currentPage - 1;
            } else {
            	this.previousPage = 1;
            }

            if (this.currentPage < totalPages) {
                this.nextPage = this.currentPage + 1;
            } else {
            	this.nextPage = totalPages;
            }
            
            this.pageList = new ArrayList<Integer>();
            for (int page = pageStart, counter = 0; counter < 10 && page <= totalPages; page++, counter++) {
            	pageList.add(page);
            }
		}
	}
	
	public Integer getCurrentPage() {
		return this.currentPage;
	}
	
	public Integer getPageSize() {
		return this.pageSize;
	}
	
	public Integer getRowCount() {
		return this.rowCount;
	}
	
	public Integer getTotalPages() {
		return totalPages;
	}
	
	public Integer getFirstPage() {
		return firstPage;
	}
	
	public Integer getPreviousPage() {
		return previousPage;
	}
	
	public Integer getNextPage() {
		return nextPage;
	}
	
	public Integer getLastPage() {
		return lastPage;
	}
	
	public List<Integer> getPageList() {
		return this.pageList;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public void setFirstPage(Integer firstPage) {
		this.firstPage = firstPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public void setPreviousPage(Integer previousPage) {
		this.previousPage = previousPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public void setPageList(List<Integer> pageList) {
		this.pageList = pageList;
	}
	
	
}
