package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;

@ManagedBean(name = "summaryReviewsMB")
@ViewScoped
public class ProjectSummaryReviewsManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8863511598108591199L;
	
	private OhLohProjectEntity project;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService overallPreferenceService;
	
	private Long overallPreferenceCount;
	private Long reviewsCount;
	private Double averageOverallPreferenceValue;
	
	public ProjectSummaryReviewsManagedBean() {
		this.project = new OhLohProjectEntity();
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
	
	public OverallPreferenceService getOverallPreferenceService() {
		return overallPreferenceService;
	}
	
	public void setOverallPreferenceService(
			OverallPreferenceService overallPreferenceService) {
		this.overallPreferenceService = overallPreferenceService;
	}
	
	public Double getAverageOverallPreferenceValue() {
		return averageOverallPreferenceValue;
	}
	
	public void setAverageOverallPreferenceValue(
			Double averageOverallPreferenceValue) {
		this.averageOverallPreferenceValue = averageOverallPreferenceValue;
	}
	
	public Long getOverallPreferenceCount() {
		return overallPreferenceCount;
	}
	
	public void setOverallPreferenceCount(Long overallPreferenceCount) {
		this.overallPreferenceCount = overallPreferenceCount;
	}
	
	public Long getReviewsCount() {
		return reviewsCount;
	}
	
	public void setReviewsCount(Long reviewsCount) {
		this.reviewsCount = reviewsCount;
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			this.overallPreferenceCount = getOverallPreferenceService().countAllLastByProject(getProject());
			this.averageOverallPreferenceValue = getOverallPreferenceService().averagePreferenceByProject(getProject());
			this.reviewsCount = getOverallPreferenceService().countAllLastReviewsByProject(getProject());
		}
	}

}
