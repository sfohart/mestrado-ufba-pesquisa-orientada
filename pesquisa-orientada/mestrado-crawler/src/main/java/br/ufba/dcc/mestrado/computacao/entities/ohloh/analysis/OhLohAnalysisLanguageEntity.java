package br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.IndexedEmbedded;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.language.OhLohLanguageEntity;

@Entity
@Table(name = OhLohAnalysisLanguageEntity.NODE_NAME)
public class OhLohAnalysisLanguageEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1672599783346624942L;

	public final static String NODE_NAME = "analysis_language";

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "language_id", insertable = false, updatable = false)
	private Long languageId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "language_id", referencedColumnName = "id")
	@IndexedEmbedded
	private OhLohLanguageEntity ohLohLanguage;

	@Column(name = "percentage")
	private String percentage;

	@Column(name = "color")
	private String color;

	@ManyToOne
	@JoinColumn(name = "analysis_languages_id", referencedColumnName = "id")
	@ContainedIn
	private OhLohAnalysisLanguagesEntity ohLohAnalysisLanguages;

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

	public OhLohAnalysisLanguagesEntity getOhLohAnalysisLanguages() {
		return ohLohAnalysisLanguages;
	}

	public void setOhLohAnalysisLanguages(
			OhLohAnalysisLanguagesEntity ohLohAnalysisLanguagesEntity) {
		this.ohLohAnalysisLanguages = ohLohAnalysisLanguagesEntity;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public OhLohLanguageEntity getOhLohLanguage() {
		return ohLohLanguage;
	}

	public void setOhLohLanguage(OhLohLanguageEntity ohLohLanguage) {
		this.ohLohLanguage = ohLohLanguage;
	}

}
