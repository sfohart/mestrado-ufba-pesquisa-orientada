package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.factoid.OhLohFactoidEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.service.base.CriteriumPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohEnlistmentService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;

@ManagedBean(name="projectDetailMB", eager=true)
@ViewScoped
public class ProjectDetailManageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3959153545005214806L;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	@ManagedProperty("#{ohLohEnlistmentService}")
	private OhLohEnlistmentService enlistmentService;
	
	@ManagedProperty("#{ohLohAnalysisService}")
	private OhLohAnalysisService analysisService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService overallPreferenceService;
	
	@ManagedProperty("#{criteriumPreferenceService}")
	private CriteriumPreferenceService criteriumPreferenceService;
	
	private OhLohProjectEntity project;
	private List<OhLohFactoidEntity> factoidList;
	private OhLohAnalysisLanguagesEntity analysisLanguages;
		
	private List<OhLohEnlistmentEntity> enlistmentList;
	private Long enlistmentCount;
	
	private Long overallPreferenceCount;
		
	private PreferenceEntity averagePreference;
	
	private String[] projectDescritionParagraphs;
	
	public ProjectDetailManageBean() {
		this.project = new OhLohProjectEntity();
		this.averagePreference = new PreferenceEntity();
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			if (getProject().getDescription() != null) {
				this.projectDescritionParagraphs = getProject().getDescription().split("\n");
			}
			
			if (getProject().getOhLohAnalysis() != null) {
				this.factoidList = getProject().getOhLohAnalysis().getOhLohFactoids();
				this.analysisLanguages = getProject().getOhLohAnalysis().getOhLohAnalysisLanguages();
			}
			
			this.enlistmentCount = getEnlistmentService().countAllByProject(getProject());
			if (enlistmentCount != null && enlistmentCount > 0) {
				this.enlistmentList = getEnlistmentService().findByProject(getProject());
			}
			
			this.overallPreferenceCount = getOverallPreferenceService().countAllLastByProject(getProject().getId());
			
			//calculando valores médios de preferência
			this.averagePreference = getOverallPreferenceService().averagePreferenceByProject(getProject().getId());
			
		}
	}
	
	public String detailProject() {
		return "projectDetail";
	}

	public OhLohProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(OhLohProjectService projectService) {
		this.projectService = projectService;
	}

	public OhLohEnlistmentService getEnlistmentService() {
		return enlistmentService;
	}

	public void setEnlistmentService(OhLohEnlistmentService enlistmentService) {
		this.enlistmentService = enlistmentService;
	}

	public OhLohAnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(OhLohAnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	public OverallPreferenceService getOverallPreferenceService() {
		return overallPreferenceService;
	}

	public void setOverallPreferenceService(
			OverallPreferenceService overallPreferenceService) {
		this.overallPreferenceService = overallPreferenceService;
	}
	
	public CriteriumPreferenceService getCriteriumPreferenceService() {
		return criteriumPreferenceService;
	}
	
	public void setCriteriumPreferenceService(
			CriteriumPreferenceService criteriumPreferenceService) {
		this.criteriumPreferenceService = criteriumPreferenceService;
	}

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

	public List<OhLohEnlistmentEntity> getEnlistmentList() {
		return enlistmentList;
	}
	
	public Long getEnlistmentCount() {
		return enlistmentCount;
	}
	
	public Long getOverallPreferenceCount() {
		return overallPreferenceCount;
	}

	public String[] getProjectDescritionParagraphs() {
		return projectDescritionParagraphs;
	}
	
	public List<OhLohFactoidEntity> getFactoidList() {
		return factoidList;
	}
	
	public OhLohAnalysisLanguagesEntity getAnalysisLanguages() {
		return analysisLanguages;
	}
	
	public PreferenceEntity getAveragePreference() {
		return averagePreference;
	}
}
