package br.ufba.dcc.mestrado.computacao.entities.ohloh.core.contributorfact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name=OhLohContributorLanguageFactEntity.NODE_NAME)
public class OhLohContributorLanguageFactEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7975310261186108199L;

	public final static String NODE_NAME = "contributor_language_fact";

	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="contributor_fact_id", referencedColumnName="id")
	private OhLohContributorFactEntity contributorFact;

	@Column(name="analysis_id")
	private Long analysisId;

	@Column(name="contributor_id")
	private Long contributorId;

	@Column(name="contributor_name")
	private String contributorName;

	@Column(name="language_id")
	private Long languageId;
	
	@Column(name="language_nice_name")
	private String languageNiceName;

	@Column(name="comment_ratio")
	private Double commentRatio;

	@Column(name="man_months")
	private Long manMonths;

	@Column(name="commits")
	private Long commits;

	@Column(name="median_commits")
	private Double medianCommits;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	public Long getContributorId() {
		return contributorId;
	}

	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}

	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	
	public String getLanguageNiceName() {
		return languageNiceName;
	}
	
	public void setLanguageNiceName(String languageNiceName) {
		this.languageNiceName = languageNiceName;
	}

	public Double getCommentRatio() {
		return commentRatio;
	}

	public void setCommentRatio(Double commentRatio) {
		this.commentRatio = commentRatio;
	}

	public Long getManMonths() {
		return manMonths;
	}

	public void setManMonths(Long manMonths) {
		this.manMonths = manMonths;
	}

	public Long getCommits() {
		return commits;
	}

	public void setCommits(Long commits) {
		this.commits = commits;
	}

	public Double getMedianCommits() {
		return medianCommits;
	}

	public void setMedianCommits(Double medianCommits) {
		this.medianCommits = medianCommits;
	}

	public OhLohContributorFactEntity getContributorFact() {
		return contributorFact;
	}
	
	public void setContributorFact(OhLohContributorFactEntity contributorFact) {
		this.contributorFact = contributorFact;
	}
	
}
