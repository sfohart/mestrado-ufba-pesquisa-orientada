package br.ufba.dcc.mestrado.computacao.ohloh.data.project;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohProjectResult {

	@XStreamImplicit(itemFieldName="project")
	private List<OhLohProjectDTO> projects;
	
	public List<OhLohProjectDTO> getProjects() {
		return projects;
	}
	
	public void setProjects(List<OhLohProjectDTO> projects) {
		this.projects = projects;
	}

}
