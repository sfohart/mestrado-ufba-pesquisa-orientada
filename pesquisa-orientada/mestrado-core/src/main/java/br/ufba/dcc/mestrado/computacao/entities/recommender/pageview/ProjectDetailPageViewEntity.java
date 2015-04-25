package br.ufba.dcc.mestrado.computacao.entities.recommender.pageview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;


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
	private OpenHubProjectEntity project;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public OpenHubProjectEntity getProject() {
		return project;
	}

	public void setProject(OpenHubProjectEntity project) {
		this.project = project;
	}
	
	

}
