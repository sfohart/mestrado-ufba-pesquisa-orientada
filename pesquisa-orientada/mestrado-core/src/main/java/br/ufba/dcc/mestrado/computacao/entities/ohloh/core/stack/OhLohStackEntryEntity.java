package br.ufba.dcc.mestrado.computacao.entities.ohloh.core.stack;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;

@Entity
@Table(name = OhLohStackEntryEntity.NODE_NAME)
public class OhLohStackEntryEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2406930252998297315L;

	public final static String NODE_NAME = "stack_entry";

	@Id
	private Long id;
	
	@Column(name = "stack_id", updatable = false, insertable = false)
	private Long stackId;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "stack_id", referencedColumnName = "id")
	private OhLohStackEntity stack;

	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "project_id", updatable = false, insertable = false)
	private Long projectId;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private OhLohProjectEntity project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OhLohStackEntity getStack() {
		return stack;
	}
	
	public void setStack(OhLohStackEntity stack) {
		this.stack = stack;
	}

	public Long getStackId() {
		return stackId;
	}

	public void setStackId(Long stackId) {
		this.stackId = stackId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

}
