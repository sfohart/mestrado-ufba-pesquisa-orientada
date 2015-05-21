package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubProjectActivityIndexDTO.NODE_NAME)
public class OpenHubProjectActivityIndexDTO implements OpenHubResultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2439053658378416084L;

	public final static String NODE_NAME = "project_activity_index";
	
	private Long value;
	
	private String description;

	@XmlElement(name = "value")
	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
