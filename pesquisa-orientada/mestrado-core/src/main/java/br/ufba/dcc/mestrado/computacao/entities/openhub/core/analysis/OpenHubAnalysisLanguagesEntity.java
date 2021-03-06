package br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.IndexedEmbedded;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name=OpenHubAnalysisLanguagesEntity.NODE_NAME)
public class OpenHubAnalysisLanguagesEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047964132725598415L;
	
	public final static String NODE_NAME = "analysis_languages";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "analysis_id", referencedColumnName = "id")
	@ContainedIn
	private OpenHubAnalysisEntity analysis;
	
	@Column(name="graph_url")
	private String graphURL;
	
	@OneToMany(mappedBy="analysisLanguages", cascade=CascadeType.ALL)
	@IndexedEmbedded
	private List<OpenHubAnalysisLanguageEntity> content;
	
	public OpenHubAnalysisLanguagesEntity() {
		this.content = new LinkedList<OpenHubAnalysisLanguageEntity>();
	}

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

	public List<OpenHubAnalysisLanguageEntity> getContent() {
		return content;
	}

	public void setContent(List<OpenHubAnalysisLanguageEntity> content) {
		this.content = content;
	}

	public OpenHubAnalysisEntity getAnalysis() {
		return analysis;
	}
	
	public void setAnalysis(OpenHubAnalysisEntity analysis) {
		this.analysis = analysis;
	}
	
}
