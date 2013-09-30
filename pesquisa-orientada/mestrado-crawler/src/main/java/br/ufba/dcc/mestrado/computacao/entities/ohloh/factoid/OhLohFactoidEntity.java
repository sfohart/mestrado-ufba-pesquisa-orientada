package br.ufba.dcc.mestrado.computacao.entities.ohloh.factoid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;

@Entity
@Table(name = OhLohFactoidEntity.NODE_NAME)
public class OhLohFactoidEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218492589477862288L;

	public final static String NODE_NAME = "factoid";

	@Id
	private Long id;
	
	@Column(name = "type")
	private String type;

	@Column(name = "analysis_id")
	private Long analysisId;

	@ManyToOne
	@JoinColumn(name = "analysis_id", referencedColumnName = "id", updatable = false, insertable = false)
	private OhLohAnalysisEntity ohLohAnalysis;

	@Column(name = "severity")
	private Long severity;

	@Column(name = "description")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSeverity() {
		return severity;
	}

	public void setSeverity(Long severity) {
		this.severity = severity;
	}

	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OhLohAnalysisEntity getOhLohAnalysis() {
		return ohLohAnalysis;
	}

	public void setOhLohAnalysis(OhLohAnalysisEntity ohLohAnalysisEntity) {
		this.ohLohAnalysis = ohLohAnalysisEntity;
	}

}
