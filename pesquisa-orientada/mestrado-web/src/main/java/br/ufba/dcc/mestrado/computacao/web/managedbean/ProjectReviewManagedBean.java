package br.ufba.dcc.mestrado.computacao.web.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

@ManagedBean(name="projectReviewMB", eager=true)
@ViewScoped
public class ProjectReviewManagedBean {
	
	private OhLohProjectEntity project;
	
	public OhLohProjectEntity getProject() {
		return project;
	}
	
	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

}
