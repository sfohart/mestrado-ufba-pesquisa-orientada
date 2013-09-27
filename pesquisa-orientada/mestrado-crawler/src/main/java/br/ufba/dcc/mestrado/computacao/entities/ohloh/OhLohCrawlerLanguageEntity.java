package br.ufba.dcc.mestrado.computacao.entities.ohloh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = OhLohCrawlerLanguageEntity.NODE_NAME)
public class OhLohCrawlerLanguageEntity  implements OhLohBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4564980589922044448L;
	
	public final static String NODE_NAME = "crawler_language";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="current_page")
	private Integer currentPage;
	
	@Column(name="total_page")
	private Integer totalPage;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	

}
