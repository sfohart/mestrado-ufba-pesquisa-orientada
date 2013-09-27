package br.ufba.dcc.mestrado.computacao.entities.ohloh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

@Entity
@Table(name = OhLohCrawlerProjectEntity.NODE_NAME)
public class OhLohCrawlerProjectEntity  implements OhLohBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4564980589922044448L;
	
	public final static String NODE_NAME = "crawler_project";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="current_page")
	private Integer currentPage;
	
	@Column(name="total_page")
	private Integer totalPage;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private OhLohProjectEntity ohLohProject;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(Integer projectCurrentPage) {
		this.currentPage = projectCurrentPage;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(Integer projectTotalPage) {
		this.totalPage = projectTotalPage;
	}

	public OhLohProjectEntity getOhLohProject() {
		return ohLohProject;
	}

	public void setOhLohProject(OhLohProjectEntity ohLohProject) {
		this.ohLohProject = ohLohProject;
	}
	
	

}
