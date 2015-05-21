package br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = OpenHubContributorLanguageFactDTO.NODE_NAME)
public class OpenHubContributorLanguageFactDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7975310261186108199L;

	public final static String NODE_NAME = "contributor_language_fact";


	private Long analysisId;

	private Long contributorId;

	private String contributorName;

	private Long languageId;

	private String languageNiceName;

	private Double commentRatio;

	private Long manMonths;

	private Long commits;

	@XmlElement(name = "analysis_id")
	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	@XmlElement(name = "contributor_id")
	public Long getContributorId() {
		return contributorId;
	}

	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}

	@XmlElement(name = "contributor_name")
	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	@XmlElement(name = "language_id")
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	@XmlElement(name = "language_nice_name")
	public String getLanguageNiceName() {
		return languageNiceName;
	}

	public void setLanguageNiceName(String languageNiceName) {
		this.languageNiceName = languageNiceName;
	}

	@XmlElement(name = "comment_ratio")
	public Double getCommentRatio() {
		return commentRatio;
	}

	public void setCommentRatio(Double commentRatio) {
		this.commentRatio = commentRatio;
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

}
