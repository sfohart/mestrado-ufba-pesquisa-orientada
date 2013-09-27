package br.ufba.dcc.mestrado.computacao.ohloh.data.analysis;

import java.sql.Timestamp;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.factoid.OhLohFactoidDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableISO8601SqlTimestampXStreamConverter;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(OhLohAnalysisDTO.NODE_NAME)
public class OhLohAnalysisDTO implements OhLohResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5783114228038874115L;

	public final static String NODE_NAME = "analysis";

	private String url;

	@XStreamAsAttribute
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long id;

	@XStreamAlias("project_id")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long projectId;

	@XStreamConverter(value = NullableISO8601SqlTimestampXStreamConverter.class)
	@XStreamAlias("updated_at")
	private Timestamp updatedAt;

	@XStreamConverter(value = NullableISO8601SqlTimestampXStreamConverter.class)
	@XStreamAlias("logged_at")
	private Timestamp loggedAt;

	@XStreamConverter(value = NullableISO8601SqlTimestampXStreamConverter.class)
	@XStreamAlias("min_month")
	private Timestamp minMonth;

	@XStreamConverter(value = NullableISO8601SqlTimestampXStreamConverter.class)
	@XStreamAlias("max_month")
	private Timestamp maxMonth;

	@XStreamAlias("twelve_month_contributor_count")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long twelveMonthContributorCount;

	@XStreamAlias("total_contributor_count")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long totalContributorCount;

	@XStreamAlias("total_code_lines")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long totalCodeLines;

	@XStreamAlias("total_commit_count")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long totalCommitCount;
	
	@XStreamAlias("twelve_month_commit_count")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long twelveMonthCommitCount;

	@XStreamAlias("factoids")
	private List<OhLohFactoidDTO> ohLohFactoids;

	@XStreamAlias("languages")
	private OhLohAnalysisLanguagesDTO ohLohAnalysisLanguages;

	@XStreamAlias("main_language_id")
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long mainLanguageId;

	@XStreamAlias("main_language_name")
	private String mainLanguageName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getLoggedAt() {
		return loggedAt;
	}

	public void setLoggedAt(Timestamp loggedAt) {
		this.loggedAt = loggedAt;
	}

	public Timestamp getMinMonth() {
		return minMonth;
	}

	public void setMinMonth(Timestamp minMonth) {
		this.minMonth = minMonth;
	}

	public Timestamp getMaxMonth() {
		return maxMonth;
	}

	public void setMaxMonth(Timestamp maxMonth) {
		this.maxMonth = maxMonth;
	}

	public Long getTwelveMonthContributorCount() {
		return twelveMonthContributorCount;
	}

	public void setTwelveMonthContributorCount(Long twelveMonthContributorCount) {
		this.twelveMonthContributorCount = twelveMonthContributorCount;
	}

	public Long getTotalContributorCount() {
		return totalContributorCount;
	}

	public void setTotalContributorCount(Long totalContributorCount) {
		this.totalContributorCount = totalContributorCount;
	}

	public Long getTotalCodeLines() {
		return totalCodeLines;
	}

	public void setTotalCodeLines(Long totalCodeLines) {
		this.totalCodeLines = totalCodeLines;
	}

	public List<OhLohFactoidDTO> getOhLohFactoids() {
		return ohLohFactoids;
	}

	public void setOhLohFactoids(List<OhLohFactoidDTO> ohLohFactoidEntities) {
		this.ohLohFactoids = ohLohFactoidEntities;
	}

	public OhLohAnalysisLanguagesDTO getOhLohAnalysisLanguages() {
		return ohLohAnalysisLanguages;
	}

	public void setOhLohAnalysisLanguages(
			OhLohAnalysisLanguagesDTO ohLohAnalysisLanguagesDTO) {
		this.ohLohAnalysisLanguages = ohLohAnalysisLanguagesDTO;
	}

	public Long getMainLanguageId() {
		return mainLanguageId;
	}

	public void setMainLanguageId(Long mainLanguageId) {
		this.mainLanguageId = mainLanguageId;
	}

	public String getMainLanguageName() {
		return mainLanguageName;
	}

	public void setMainLanguageName(String mainLanguageName) {
		this.mainLanguageName = mainLanguageName;
	}

	public Long getTotalCommitCount() {
		return totalCommitCount;
	}

	public void setTotalCommitCount(Long totalCommitCount) {
		this.totalCommitCount = totalCommitCount;
	}

	public Long getTwelveMonthCommitCount() {
		return twelveMonthCommitCount;
	}

	public void setTwelveMonthCommitCount(Long twelveMonthCommitCount) {
		this.twelveMonthCommitCount = twelveMonthCommitCount;
	}

}
