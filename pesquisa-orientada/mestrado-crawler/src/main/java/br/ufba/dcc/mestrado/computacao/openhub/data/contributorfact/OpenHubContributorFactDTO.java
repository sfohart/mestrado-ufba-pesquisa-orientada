package br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubContributorFactDTO.NODE_NAME)
public class OpenHubContributorFactDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6755139715374660722L;

	public final static String NODE_NAME = "contributor_fact";
	
	private Long contributorId;

	private Long accountId;

	private String accountName;

	private Long analysisId;

	private String contributorName;

	private Long primaryLanguageId;

	private String primaryLanguageNiceName;

	private Double commentRatio;

	private Date firstCommitTime;

	private Date lastCommitTime;

	private Long manMonths;

	private Long commits;

	private Double medianCommits;

	private List<OpenHubContributorLanguageFactDTO> contributorLanguageFacts;

	@XmlElement(name = "contributor_id")
	public Long getContributorId() {
		return contributorId;
	}

	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}

	@XmlElement(name = "account_id")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@XmlElement(name = "account_name")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@XmlElement(name = "analysis_id")
	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	@XmlElement(name = "contributor_name")
	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	@XmlElement(name = "primary_language_id")
	public Long getPrimaryLanguageId() {
		return primaryLanguageId;
	}

	public void setPrimaryLanguageId(Long primaryLanguageId) {
		this.primaryLanguageId = primaryLanguageId;
	}

	@XmlElement(name = "primary_language_nice_name")
	public String getPrimaryLanguageNiceName() {
		return primaryLanguageNiceName;
	}

	public void setPrimaryLanguageNiceName(String primaryLanguageNiceName) {
		this.primaryLanguageNiceName = primaryLanguageNiceName;
	}

	@XmlElement(name = "comment_ratio")
	public Double getCommentRatio() {
		return commentRatio;
	}

	public void setCommentRatio(Double commentRatio) {
		this.commentRatio = commentRatio;
	}

	@XmlElement(name = "first_commit_time")
	@XmlSchemaType(name = "date")
	public Date getFirstCommitTime() {
		return firstCommitTime;
	}

	public void setFirstCommitTime(Date firstCommitTime) {
		this.firstCommitTime = firstCommitTime;
	}

	@XmlElement(name = "last_commit_time")
	@XmlSchemaType(name = "date")
	public Date getLastCommitTime() {
		return lastCommitTime;
	}

	public void setLastCommitTime(Date lastCommitTime) {
		this.lastCommitTime = lastCommitTime;
	}

	@XmlElement(name = "man_months")
	public Long getManMonths() {
		return manMonths;
	}

	public void setManMonths(Long manMonths) {
		this.manMonths = manMonths;
	}

	@XmlElement(name = "commits")
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

	@XmlElement(name = "contributor_language_facts")
	public List<OpenHubContributorLanguageFactDTO> getContributorLanguageFacts() {
		return contributorLanguageFacts;
	}
	
	public void setContributorLanguageFacts(
			List<OpenHubContributorLanguageFactDTO> contributorLanguageFacts) {
		this.contributorLanguageFacts = contributorLanguageFacts;
	}
}
