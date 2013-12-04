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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.service.base.CriteriumPreferenceService;
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
	
	@ManagedProperty("#{criteriumPreferenceService}")
	private CriteriumPreferenceService criteriumPreferenceService;
	
	private Long overallPreferenceCount;
	private Long reviewsCount;
	
	private final PreferenceEntity averagePreference;
	
	
	public ProjectSummaryReviewsManagedBean() {
		this.project = new OhLohProjectEntity();
		this.averagePreference = new PreferenceEntity();
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
	
	public CriteriumPreferenceService getCriteriumPreferenceService() {
		return criteriumPreferenceService;
	}
	
	public void setCriteriumPreferenceService(
			CriteriumPreferenceService criteriumPreferenceService) {
		this.criteriumPreferenceService = criteriumPreferenceService;
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
	
	public PreferenceEntity getAveragePreference() {
		return averagePreference;
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			this.overallPreferenceCount = getOverallPreferenceService().countAllLastByProject(getProject());
			this.reviewsCount = getOverallPreferenceService().countAllLastReviewsByProject(getProject());
			
			this.averagePreference.setValue(getOverallPreferenceService().averagePreferenceByProject(getProject()));
			
			Map<RecommenderCriteriumEntity, Double> averageByCriterium = 
					getCriteriumPreferenceService().averagePreferenceByProject(getProject().getId());
			
			List<PreferenceEntryEntity> preferenceEntryList = new ArrayList<>();
			for (Map.Entry<RecommenderCriteriumEntity, Double> entry : averageByCriterium.entrySet()) {
				PreferenceEntryEntity preferenceEntry = new PreferenceEntryEntity();
				preferenceEntry.setPreference(averagePreference);
				preferenceEntry.setCriterium(entry.getKey());
				preferenceEntry.setValue(entry.getValue());
				
				preferenceEntryList.add(preferenceEntry);
			}
			
			Comparator<PreferenceEntryEntity> entryComparator = new Comparator<PreferenceEntryEntity>() {
				@Override
				public int compare(PreferenceEntryEntity o1, PreferenceEntryEntity o2) {
					return o1.getCriterium().getName().compareTo(o2.getCriterium().getName());
				}
			};
			
			Collections.sort(preferenceEntryList, entryComparator);
			
			averagePreference.setPreferenceEntryList(preferenceEntryList);
		}
	}
	
	

}
