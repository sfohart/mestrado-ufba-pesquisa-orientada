package br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.IndexedEmbedded;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.language.OpenHubLanguageEntity;

@Entity
@Table(name = OpenHubAnalysisLanguageEntity.NODE_NAME)
public class OpenHubAnalysisLanguageEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1672599783346624942L;

	public final static String NODE_NAME = "analysis_language";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "language_id", insertable = false, updatable = false)
	private Long languageId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "language_id", referencedColumnName = "id")
	@IndexedEmbedded
	private OpenHubLanguageEntity language;

	@Column(name = "percentage")
	private String percentage;

	@Column(name = "color")
	private String color;

	@ManyToOne
	@JoinColumn(name = "analysis_languages_id", referencedColumnName = "id")
	@ContainedIn
	private OpenHubAnalysisLanguagesEntity analysisLanguages;

	@Column(name = "entry_content")
	private String entryContent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getEntryContent() {
		return entryContent;
	}

	public void setEntryContent(String entryContent) {
		this.entryContent = entryContent;
	}

	public OpenHubAnalysisLanguagesEntity getAnalysisLanguages() {
		return analysisLanguages;
	}
	
	public void setAnalysisLanguages(
			OpenHubAnalysisLanguagesEntity analysisLanguages) {
		this.analysisLanguages = analysisLanguages;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public OpenHubLanguageEntity getLanguage() {
		return language;
	}
	
	public void setLanguage(OpenHubLanguageEntity language) {
		this.language = language;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((entryContent == null) ? 0 : entryContent.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((languageId == null) ? 0 : languageId.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenHubAnalysisLanguageEntity other = (OpenHubAnalysisLanguageEntity) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (entryContent == null) {
			if (other.entryContent != null)
				return false;
		} else if (!entryContent.equals(other.entryContent))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (languageId == null) {
			if (other.languageId != null)
				return false;
		} else if (!languageId.equals(other.languageId))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		return true;
	}
	
	

}
