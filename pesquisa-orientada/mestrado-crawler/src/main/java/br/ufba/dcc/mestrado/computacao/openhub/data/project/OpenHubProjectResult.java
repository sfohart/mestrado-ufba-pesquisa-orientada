package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = OpenHubProjectResult.NODE_NAME)
public class OpenHubProjectResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6180783452341172860L;

	public final static String NODE_NAME = "result";

	private List<OpenHubProjectDTO> projects;
	
	
	@XmlElement(name = "project")
	public List<OpenHubProjectDTO> getProjects() {
		return projects;
	}
	
	public void setProjects(List<OpenHubProjectDTO> projects) {
		this.projects = projects;
	}

}
