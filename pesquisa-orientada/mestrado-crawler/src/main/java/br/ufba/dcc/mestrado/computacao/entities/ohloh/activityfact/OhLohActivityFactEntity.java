package br.ufba.dcc.mestrado.computacao.entities.ohloh.activityfact;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

@Entity
@Table(name=OhLohActivityFactEntity.NODE_NAME)
public class OhLohActivityFactEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1220433997459663718L;

	public final static String NODE_NAME = "activity_fact";
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="month")
	private Timestamp month;
	
	@Column(name="code_added")
	private Long codeAdded;
	
	@Column(name="code_removed")
	private Long codeRemoved;
	
	@Column(name="comments_added")
	private Long commentsAdded;
	
	@Column(name="comments_removed")
	private Long commentsRemoved;
	
	@Column(name="blanks_added")
	private Long blanksAdded;
	
	@Column(name="blanks_removed")
	private Long blanksRemoved;
	
	@Column(name="commits")
	private Long commits;
	
	@Column(name="contributors")
	private Long contributors;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	@ContainedIn
	private OhLohProjectEntity ohlohProject;

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

	public Long getCodeAdded() {
		return codeAdded;
	}

	public void setCodeAdded(Long codeAdded) {
		this.codeAdded = codeAdded;
	}

	public Long getCodeRemoved() {
		return codeRemoved;
	}

	public void setCodeRemoved(Long codeRemoved) {
		this.codeRemoved = codeRemoved;
	}

	public Long getCommentsAdded() {
		return commentsAdded;
	}

	public void setCommentsAdded(Long commentsAdded) {
		this.commentsAdded = commentsAdded;
	}

	public Long getCommentsRemoved() {
		return commentsRemoved;
	}

	public void setCommentsRemoved(Long commentsRemoved) {
		this.commentsRemoved = commentsRemoved;
	}

	public Long getBlanksAdded() {
		return blanksAdded;
	}

	public void setBlanksAdded(Long blanksAdded) {
		this.blanksAdded = blanksAdded;
	}

	public Long getBlanksRemoved() {
		return blanksRemoved;
	}

	public void setBlanksRemoved(Long blanksRemoved) {
		this.blanksRemoved = blanksRemoved;
	}

	public Long getCommits() {
		return commits;
	}

	public void setCommits(Long commits) {
		this.commits = commits;
	}

	public Long getContributors() {
		return contributors;
	}

	public void setContributors(Long contributors) {
		this.contributors = contributors;
	}

		
}
