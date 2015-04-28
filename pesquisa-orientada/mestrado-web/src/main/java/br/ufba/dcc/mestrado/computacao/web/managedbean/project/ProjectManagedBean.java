
package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;

public class ProjectManagedBean implements Serializable {

	protected static final String SELECTED_PROJECT_PARAM = "selectedProjectId";

	/**
	 * 
	 */
	private static final long serialVersionUID = 8640323269826161433L;

	@ManagedProperty("#{projectService}")
	private ProjectService projectService;
	
	private OpenHubProjectEntity project;

	public ProjectManagedBean() {
		this.project = new OpenHubProjectEntity();
	}
	
	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public OpenHubProjectEntity getProject() {
		return project;
	}

	public void setProject(OpenHubProjectEntity project) {
		this.project = project;
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			OpenHubProjectEntity project = getProjectService().findById(getProject().getId());
			setProject(project);
		}
	}

}

