package br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.pageview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;


@Entity
@Table(name = ProjectDetailPageViewEntity.NODE_NAME)
public class ProjectDetailPageViewEntity extends PageViewEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2843370755220575484L;
	
	public final static String NODE_NAME = "project_detail_page_view";
	
	
	@Column(name = "project_id", updatable = false, insertable = false)
	private Long projectId;

	@ManyToOne(optional=false)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private OhLohProjectEntity project;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}
	
	

}
