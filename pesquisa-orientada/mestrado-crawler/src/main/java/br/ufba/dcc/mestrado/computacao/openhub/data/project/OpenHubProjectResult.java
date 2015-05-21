package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubProjectResult implements OpenHubBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6180783452341172860L;

	private List<OpenHubProjectDTO> projects;
	
	
	@XmlElement(name = "project")
	public List<OpenHubProjectDTO> getProjects() {
		return projects;
	}
	
	public void setProjects(List<OpenHubProjectDTO> projects) {
		this.projects = projects;
	}

}
