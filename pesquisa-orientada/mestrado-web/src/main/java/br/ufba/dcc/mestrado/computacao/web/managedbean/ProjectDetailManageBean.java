package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;

@ManagedBean(name="projectDetailMB", eager=true)
@ViewScoped
public class ProjectDetailManageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3959153545005214806L;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	private OhLohProjectEntity project;
	
	private String[] projectDescritionParagraphs;
	
	public ProjectDetailManageBean() {
		this.project = new OhLohProjectEntity();
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			if (this.project.getDescription() != null) {
				this.projectDescritionParagraphs = project.getDescription().split("\n");
			}
		}
	}
	
	public OhLohProjectEntity getProject() {
		return project;
	}
	
	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}
	
	public OhLohProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(OhLohProjectService projectService) {
		this.projectService = projectService;
	}

	public String[] getProjectDescritionParagraphs() {
		return projectDescritionParagraphs;
	}
	
	public String detailProject() {
		return "projectDetail";
	}
}
