package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.ProjectPreferenceInfo;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;

@ManagedBean(name="indexMB")
@ViewScoped
public class IndexManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1409944916651513725L;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService ohLohProjectService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService overallPreferenceService;
	
	@ManagedProperty("#{recommenderCriteriumService}")
	private RecommenderCriteriumService recommenderCriteriumService;
	
	
	private List<ProjectPreferenceInfo> topTenReviewedProjectList;
	
	private ProjectPreferenceInfo mostReviewedProjectPreferenceInfo;
		
	public IndexManagedBean() {
	}

	public void init(ComponentSystemEvent event) {
		this.topTenReviewedProjectList = getOverallPreferenceService().findAllProjectPreferenceInfo(0, 10);
		if (this.topTenReviewedProjectList != null && ! this.topTenReviewedProjectList.isEmpty()) {
			this.mostReviewedProjectPreferenceInfo = this.topTenReviewedProjectList.get(0);
		}
	}
	
	public OhLohProjectService getOhLohProjectService() {
		return ohLohProjectService;
	}

	public void setOhLohProjectService(OhLohProjectService ohLohProjectService) {
		this.ohLohProjectService = ohLohProjectService;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public OverallPreferenceService getOverallPreferenceService() {
		return overallPreferenceService;
	}
	
	public void setOverallPreferenceService(
			OverallPreferenceService overallPreferenceService) {
		this.overallPreferenceService = overallPreferenceService;
	}
	
	public RecommenderCriteriumService getRecommenderCriteriumService() {
		return recommenderCriteriumService;
	}
	
	public void setRecommenderCriteriumService(
			RecommenderCriteriumService recommenderCriteriumService) {
		this.recommenderCriteriumService = recommenderCriteriumService;
	}
	
	public Long getProjectCount() {
		return getOhLohProjectService().countAll();
	}
	
	public Long getUserCount() {
		return getUserService().countAll();
	}
	
	public Long getPreferencesCount() {
		return getOverallPreferenceService().countAllLast();
	}
	
	public Long getRecommenderCriteriumCount() {
		return  getRecommenderCriteriumService().countAll();
	}
	
	public List<ProjectPreferenceInfo> getTopTenReviewedProjectList() {
		return topTenReviewedProjectList;
	}
	
	public ProjectPreferenceInfo getMostReviewedProjectPreferenceInfo() {
		return mostReviewedProjectPreferenceInfo;
	}
	
}
