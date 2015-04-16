package br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact;

import java.sql.Timestamp;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableDoubleXStreamConverter;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OpenHubContributorFactDTO.NODE_NAME)
public class OpenHubContributorFactDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6755139715374660722L;

	public final static String NODE_NAME = "contributor_fact";
	
	@XStreamAsAttribute	
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long id;

	@XStreamAlias("contributor_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long contributorId;

	@XStreamAlias("account_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long accountId;

	@XStreamAlias("account_name")
	private String accountName;

	@XStreamAlias("analysis_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long analysisId;

	@XStreamAlias("contributor_name")
	private String contributorName;

	@XStreamAlias("primary_language_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long primaryLanguageId;

	@XStreamAlias("primary_language_nice_name")
	private String primaryLanguageNiceName;

	@XStreamAlias("comment_ratio")
	@XStreamConverter(value = NullableDoubleXStreamConverter.class)
	private Double commentRatio;

	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	@XStreamAlias("first_commit_time")
	private Timestamp firstCommitTime;

	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	@XStreamAlias("last_commit_time")
	private Timestamp lastCommitTime;

	@XStreamAlias("man_months")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long manMonths;

	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long commits;

	@XStreamConverter(value = NullableDoubleXStreamConverter.class)
	@XStreamAlias("median_commits")
	private Double medianCommits;

	@XStreamAlias("contributor_language_facts")
	private List<OpenHubContributorLanguageFactDTO> contributorLanguageFacts;

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

	public List<OpenHubContributorLanguageFactDTO> getContributorLanguageFacts() {
		return contributorLanguageFacts;
	}
	
	public void setContributorLanguageFacts(
			List<OpenHubContributorLanguageFactDTO> contributorLanguageFacts) {
		this.contributorLanguageFacts = contributorLanguageFacts;
	}
}
