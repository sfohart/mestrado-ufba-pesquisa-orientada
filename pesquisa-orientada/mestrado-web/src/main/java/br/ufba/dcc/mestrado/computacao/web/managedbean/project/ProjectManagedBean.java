package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;

@ManagedBean(name="projectMB")
@ViewScoped
public class ProjectManagedBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8640323269826161433L;

	@ManagedProperty("#{projectService}")
	private ProjectService projectService;
	
	private OhLohProjectEntity project;

	public ProjectManagedBean() {
		this.project = new OhLohProjectEntity();
	}
	
	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			OhLohProjectEntity project = getProjectService().findById(getProject().getId());
			setProject(project);
		}
	}

}
