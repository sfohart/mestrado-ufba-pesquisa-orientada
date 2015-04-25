package br.ufba.dcc.mestrado.computacao.entities.openhub.core.enlistment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

@Entity
@Table(name = OpenHubEnlistmentEntity.NODE_NAME)
public class OpenHubEnlistmentEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -72467167025120985L;

	public final static String NODE_NAME = "enlistment";

	@Id
	private Long id;
	
	@Column(name = "project_id")
	private Long projectId;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OpenHubProjectEntity project;

	@Column(name = "repository_id", insertable = false, updatable = false)
	private Long repositoryId;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "repository_id", referencedColumnName = "id")
	private OpenHubRepositoryEntity repository;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OpenHubProjectEntity getProject() {
		return project;
	}
	
	public void setProject(OpenHubProjectEntity project) {
		this.project = project;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public OpenHubRepositoryEntity getRepository() {
		return repository;
	}
	
	public void setRepository(OpenHubRepositoryEntity repository) {
		this.repository = repository;
	}

}
