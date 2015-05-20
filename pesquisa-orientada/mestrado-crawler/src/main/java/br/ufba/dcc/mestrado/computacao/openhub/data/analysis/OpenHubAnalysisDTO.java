package br.ufba.dcc.mestrado.computacao.openhub.data.analysis;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.factoid.OpenHubFactoidDTO;

@XmlRootElement(name = OpenHubAnalysisDTO.NODE_NAME)
public class OpenHubAnalysisDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5783114228038874115L;

	public final static String NODE_NAME = "analysis";

	private String url;

	private String id;

	private Long projectId;

	private Date updatedAt;

	private Date loggedAt;

	private Date minMonth;

	private Date maxMonth;

	private Long twelveMonthContributorCount;

	private Long totalContributorCount;

	private Long totalCodeLines;

	private Long totalCommitCount;
	
	private Long twelveMonthCommitCount;

	private List<OpenHubFactoidDTO> factoids;

	private OpenHubAnalysisLanguagesDTO analysisLanguages;

	private Long mainLanguageId;

	private String mainLanguageName;

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@XmlElement(name = "updated_at")
	@XmlSchemaType(name = "date")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement(name = "logged_at")
	@XmlSchemaType(name = "date")
	public Date getLoggedAt() {
		return loggedAt;
	}

	public void setLoggedAt(Date loggedAt) {
		this.loggedAt = loggedAt;
	}

	@XmlElement(name = "min_month")
	@XmlSchemaType(name = "date")
	public Date getMinMonth() {
		return minMonth;
	}

	public void setMinMonth(Date minMonth) {
		this.minMonth = minMonth;
	}

	@XmlElement(name = "max_month")
	@XmlSchemaType(name = "date")
	public Date getMaxMonth() {
		return maxMonth;
	}

	public void setMaxMonth(Date maxMonth) {
		this.maxMonth = maxMonth;
	}

	@XmlElement(name = "twelve_month_contributor_count")
	public Long getTwelveMonthContributorCount() {
		return twelveMonthContributorCount;
	}

	public void setTwelveMonthContributorCount(Long twelveMonthContributorCount) {
		this.twelveMonthContributorCount = twelveMonthContributorCount;
	}

	@XmlElement(name = "total_contributor_count")
	public Long getTotalContributorCount() {
		return totalContributorCount;
	}

	public void setTotalContributorCount(Long totalContributorCount) {
		this.totalContributorCount = totalContributorCount;
	}

	@XmlElement(name = "total_code_lines")
	public Long getTotalCodeLines() {
		return totalCodeLines;
	}

	public void setTotalCodeLines(Long totalCodeLines) {
		this.totalCodeLines = totalCodeLines;
	}

	@XmlElementWrapper(name = "factoids", required = false)
	@XmlElement(name = "factoid")
	public List<OpenHubFactoidDTO> getFactoids() {
		return factoids;
	}
	
	public void setFactoids(List<OpenHubFactoidDTO> factoids) {
		this.factoids = factoids;
	}

	@XmlElement(name = "languages")
	public OpenHubAnalysisLanguagesDTO getAnalysisLanguages() {
		return analysisLanguages;
	}
	
	public void setAnalysisLanguages(OpenHubAnalysisLanguagesDTO analysisLanguages) {
		this.analysisLanguages = analysisLanguages;
	}

	@XmlElement(name = "main_language_id")
	public Long getMainLanguageId() {
		return mainLanguageId;
	}

	public void setMainLanguageId(Long mainLanguageId) {
		this.mainLanguageId = mainLanguageId;
	}

	@XmlElement(name = "main_language_name")
	public String getMainLanguageName() {
		return mainLanguageName;
	}

	public void setMainLanguageName(String mainLanguageName) {
		this.mainLanguageName = mainLanguageName;
	}

	@XmlElement(name = "total_commit_count")
	public Long getTotalCommitCount() {
		return totalCommitCount;
	}

	public void setTotalCommitCount(Long totalCommitCount) {
		this.totalCommitCount = totalCommitCount;
	}

	@XmlElement(name = "twelve_month_commit_count")
	public Long getTwelveMonthCommitCount() {
		return twelveMonthCommitCount;
	}

	public void setTwelveMonthCommitCount(Long twelveMonthCommitCount) {
		this.twelveMonthCommitCount = twelveMonthCommitCount;
	}

}
