package br.ufba.dcc.mestrado.computacao.openhub.data.factoid;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubFactoidDTO.NODE_NAME)
public class OpenHubFactoidDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218492589477862288L;

	public final static String NODE_NAME = "factoid";
	
	private String id;
	
	private String type;
	
	private Long analysisId;

	private Long severity;

	
	private String description;
	

	@XmlID
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement
	public Long getSeverity() {
		return severity;
	}

	public void setSeverity(Long severity) {
		this.severity = severity;
	}

	@XmlElement(name = "analysis_id")
	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
