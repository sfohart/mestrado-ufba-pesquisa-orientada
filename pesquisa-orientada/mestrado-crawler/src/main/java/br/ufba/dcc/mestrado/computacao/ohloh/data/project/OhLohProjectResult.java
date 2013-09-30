package br.ufba.dcc.mestrado.computacao.ohloh.data.project;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohProjectResult {

	@XStreamImplicit(itemFieldName="project")
	private List<OhLohProjectDTO> ohLohProjects;
	
	public List<OhLohProjectDTO> getOhLohProjects() {
		return ohLohProjects;
	}
	
	public void setOhLohProjects(List<OhLohProjectDTO> ohLohProjects) {
		this.ohLohProjects = ohLohProjects;
	}
	
}
