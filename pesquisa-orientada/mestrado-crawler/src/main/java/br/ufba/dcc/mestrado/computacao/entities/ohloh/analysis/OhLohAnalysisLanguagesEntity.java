package br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohBaseEntity;

@Entity
@Table(name=OhLohAnalysisLanguagesEntity.NODE_NAME)
public class OhLohAnalysisLanguagesEntity implements OhLohBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047964132725598415L;
	
	public final static String NODE_NAME = "analysis_languages";

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(mappedBy="ohLohAnalysisLanguages")
	@PrimaryKeyJoinColumn
	private OhLohAnalysisEntity ohLohAnalysis;
	
	@Column(name="graph_url")
	private String graphURL;
	
	@OneToMany(mappedBy="ohLohAnalysisLanguages")
	private List<OhLohAnalysisLanguageEntity> content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGraphURL() {
		return graphURL;
	}

	public void setGraphURL(String graphURL) {
		this.graphURL = graphURL;
	}

	public List<OhLohAnalysisLanguageEntity> getContent() {
		return content;
	}

	public void setContent(List<OhLohAnalysisLanguageEntity> content) {
		this.content = content;
	}

	public OhLohAnalysisEntity getOhLohAnalysis() {
		return ohLohAnalysis;
	}

	public void setOhLohAnalysis(OhLohAnalysisEntity ohLohAnalysis) {
		this.ohLohAnalysis = ohLohAnalysis;
	}
	
	
}
