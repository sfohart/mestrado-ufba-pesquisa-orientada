package br.ufba.dcc.mestrado.computacao.entities.ohloh.contributorfact;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name=OhLohContributorFactEntity.NODE_NAME)
public class OhLohContributorFactEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6755139715374660722L;

	public final static String NODE_NAME = "contributor_fact";

	@Id
	private Long id;
	
	@Column(name="contributor_id")
	private Long contributorId;
	
	@Column(name="account_id")
	private Long accountId;
	
	@Column(name="account_name")
	private String accountName;
	
	@Column(name="analysis_id")
	private Long analysisId;
	
	@Column(name="contributor_name")
	private String contributorName;
	
	@Column(name="primary_language_id")
	private Long primaryLanguageId;
	
	@Column(name="primary_language_nice_name")
	private String primaryLanguageNiceName;
	
	@Column(name="comment_ratio")
	private Double commentRatio;
	
	@Column(name="first_commit_time")
	private Timestamp firstCommitTime;
	
	@Column(name="last_commit_time")
	private Timestamp lastCommitTime;
	
	@Column(name="man_months")
	private Long manMonths;
	
	@Column(name="commits")
	private Long commits;
	
	@Column(name="median_commits")
	private Double medianCommits;
	
	@OneToMany(mappedBy="contributorFact")
	private List<OhLohContributorLanguageFactEntity> contributorLanguageFacts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContributorId() {
		return contributorId;
	}

	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	public Long getPrimaryLanguageId() {
		return primaryLanguageId;
	}

	public void setPrimaryLanguageId(Long primaryLanguageId) {
		this.primaryLanguageId = primaryLanguageId;
	}

	public String getPrimaryLanguageNiceName() {
		return primaryLanguageNiceName;
	}

	public void setPrimaryLanguageNiceName(String primaryLanguageNiceName) {
		this.primaryLanguageNiceName = primaryLanguageNiceName;
	}

	public Double getCommentRatio() {
		return commentRatio;
	}

	public void setCommentRatio(Double commentRatio) {
		this.commentRatio = commentRatio;
	}

	public Timestamp getFirstCommitTime() {
		return firstCommitTime;
	}

	public void setFirstCommitTime(Timestamp firstCommitTime) {
		this.firstCommitTime = firstCommitTime;
	}

	public Timestamp getLastCommitTime() {
		return lastCommitTime;
	}

	public void setLastCommitTime(Timestamp lastCommitTime) {
		this.lastCommitTime = lastCommitTime;
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
	
	public List<OhLohContributorLanguageFactEntity> getContributorLanguageFacts() {
		return contributorLanguageFacts;
	}
	
	public void setContributorLanguageFacts(
			List<OhLohContributorLanguageFactEntity> contributorLanguageFacts) {
		this.contributorLanguageFacts = contributorLanguageFacts;
	}
}
