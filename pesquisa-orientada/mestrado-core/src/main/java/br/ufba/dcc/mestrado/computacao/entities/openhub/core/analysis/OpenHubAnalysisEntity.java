package br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.factoid.OpenHubFactoidEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;

@Entity
@Table(name = OpenHubAnalysisEntity.NODE_NAME)
public class OpenHubAnalysisEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5783114228038874115L;

	public final static String NODE_NAME = "analysis";

	@Id
	private Long id;

	@Column(name = "url")
	private String url;

	@Column(name = "project_id", updatable = false, insertable = false)
	private Long projectId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	@ContainedIn
	private OpenHubProjectEntity project;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "logged_at")
	private Timestamp loggedAt;

	@Column(name = "min_month")
	private Timestamp minMonth;

	@Column(name = "max_month")
	private Timestamp maxMonth;

	@Column(name = "twelve_month_contributor_count")
	private Long twelveMonthContributorCount;

	@Column(name = "total_contributor_count")
	private Long totalContributorCount;

	@Column(name = "total_commit_count")
	private Long totalCommitCount;

	@Column(name = "total_code_lines")
	private Long totalCodeLines;

	@OneToMany(mappedBy = "analysis", cascade=CascadeType.ALL)
	private List<OpenHubFactoidEntity> factoids;

	@ManyToOne(cascade = CascadeType.ALL)	
	@JoinColumn(name = "analysis_languages_id", referencedColumnName = "id")
	@IndexedEmbedded
	private OpenHubAnalysisLanguagesEntity analysisLanguages;

	@Column(name = "main_language_id")
	private Long mainLanguageId;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "main_language_name")
	private String mainLanguageName;	
	
	@Column(name = "twelve_month_commit_count")	
	private Long twelveMonthCommitCount;
	
	public OpenHubAnalysisEntity() {
		this.factoids = new LinkedList<OpenHubFactoidEntity>();
	}

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

	public OpenHubProjectEntity getProject() {
		return project;
	}
	
	public void setProject(OpenHubProjectEntity project) {
		this.project = project;
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

	public Long getTotalCodeLines() {
		return totalCodeLines;
	}

	public void setTotalCodeLines(Long totalCodeLines) {
		this.totalCodeLines = totalCodeLines;
	}

	public List<OpenHubFactoidEntity> getFactoids() {
		return factoids;
	}
	
	public void setFactoids(List<OpenHubFactoidEntity> factoids) {
		this.factoids = factoids;
	}
	
	public OpenHubAnalysisLanguagesEntity getAnalysisLanguages() {
		return analysisLanguages;
	}
	
	public void setAnalysisLanguages(
			OpenHubAnalysisLanguagesEntity analysisLanguages) {
		this.analysisLanguages = analysisLanguages;
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

	public Long getTotalContributorCount() {
		return totalContributorCount;
	}

	public void setTotalContributorCount(Long totalContributorCount) {
		this.totalContributorCount = totalContributorCount;
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
