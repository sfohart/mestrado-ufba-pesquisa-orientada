package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubProjectResult {

	@XStreamImplicit(itemFieldName="project")
	private List<OpenHubProjectDTO> projects;
	
	public List<OpenHubProjectDTO> getProjects() {
		return projects;
	}
	
	public void setProjects(List<OpenHubProjectDTO> projects) {
		this.projects = projects;
	}

}
