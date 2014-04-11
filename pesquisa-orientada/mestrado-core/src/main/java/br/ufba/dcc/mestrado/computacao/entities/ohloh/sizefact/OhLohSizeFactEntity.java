package br.ufba.dcc.mestrado.computacao.entities.ohloh.sizefact;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

@Entity
@Table(name = OhLohSizeFactEntity.NODE_NAME)
public class OhLohSizeFactEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5308357200941776405L;

	public final static String NODE_NAME = "size_fact";

	@Id
	private Long id;
	
	@Column(name = "month")
	private Timestamp month;

	@Column(name = "code")
	private Long code;

	@Column(name = "comments")
	private Long comments;

	@Column(name = "blanks")
	private Long blanks;

	@Column(name = "comment_ratio")
	private Double commentRatio;

	@Column(name = "commits")
	private String commits;

	@Column(name = "man_months")
	private Long manMonths;
	
	@Column(name = "project_id", updatable = false, insertable = false)
	private Long projectId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	@ContainedIn
	private OhLohProjectEntity project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getMonth() {
		return month;
	}

	public void setMonth(Timestamp month) {
		this.month = month;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Long getBlanks() {
		return blanks;
	}

	public void setBlanks(Long blanks) {
		this.blanks = blanks;
	}

	public Double getCommentRatio() {
		return commentRatio;
	}

	public void setCommentRatio(Double commentRatio) {
		this.commentRatio = commentRatio;
	}

	public String getCommits() {
		return commits;
	}

	public void setCommits(String commits) {
		this.commits = commits;
	}

	public Long getManMonths() {
		return manMonths;
	}

	public void setManMonths(Long manMonths) {
		this.manMonths = manMonths;
	}

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
