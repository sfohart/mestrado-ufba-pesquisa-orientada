package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XStreamAlias(OpenHubProjectActivityIndexDTO.NODE_NAME)
public class OpenHubProjectActivityIndexDTO implements OpenHubResultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2439053658378416084L;

	public final static String NODE_NAME = "project_activity_index";
	
	private Long value;
	
	private String description;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
