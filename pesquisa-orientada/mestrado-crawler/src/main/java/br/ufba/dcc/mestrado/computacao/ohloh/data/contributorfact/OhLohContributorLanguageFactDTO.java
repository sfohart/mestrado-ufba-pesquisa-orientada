package br.ufba.dcc.mestrado.computacao.ohloh.data.contributorfact;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableDoubleXStreamConverter;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(OhLohContributorLanguageFactDTO.NODE_NAME)
public class OhLohContributorLanguageFactDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7975310261186108199L;

	public final static String NODE_NAME = "contributor_language_fact";

	@XStreamAsAttribute	
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long id;

	@XStreamAlias("analysis_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long analysisId;

	@XStreamAlias("contributor_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long contributorId;

	@XStreamAlias("contributor_name")
	private String contributorName;

	@XStreamAlias("language_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long languageId;

	@XStreamAlias("language_nice_name")
	private String languageNiceName;

	@XStreamAlias("comment_ratio")
	@XStreamConverter(value = NullableDoubleXStreamConverter.class)
	private Double commentRatio;

	@XStreamAlias("man_months")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long manMonths;

	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long commits;

	@XStreamConverter(value = NullableDoubleXStreamConverter.class)
	@XStreamAlias("median_commits")
	private Double medianCommits;

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

}
