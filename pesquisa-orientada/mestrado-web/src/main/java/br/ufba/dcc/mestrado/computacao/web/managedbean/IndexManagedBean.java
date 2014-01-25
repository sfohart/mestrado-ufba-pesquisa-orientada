package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.ProjectPreferenceInfo;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.basic.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

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
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService overallPreferenceService;
	
	@ManagedProperty("#{recommenderCriteriumService}")
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@ManagedProperty("#{projectDetailPageViewService}")
	private ProjectDetailPageViewService pageViewService;
	
	private List<ProjectPreferenceInfo> topTenReviewedProjectList;
	private List<ProjectDetailPageViewInfo> topTenViewedProjectList;
	
	private List<OhLohProjectEntity> projectViewedList;
	
	private ProjectPreferenceInfo mostReviewedProjectPreferenceInfo;
	private ProjectDetailPageViewInfo mostViewedProjectDetailInfo;
		
	public IndexManagedBean() {
	}

	public void init(ComponentSystemEvent event) {
		this.topTenReviewedProjectList = getOverallPreferenceService().findAllProjectPreferenceInfo(0, 10);
		this.topTenViewedProjectList = getPageViewService().findAllProjectDetailPageViewInfo(0, 10);
		
		
		this.projectViewedList = getPageViewService().findAllProjectRecentlyViewed(
				getUserDetailsService().loadFullLoggedUser(), 
				0, 
				5);
		
		if (this.topTenReviewedProjectList != null && ! this.topTenReviewedProjectList.isEmpty()) {
			this.mostReviewedProjectPreferenceInfo = this.topTenReviewedProjectList.get(0);
		}
		
		if (this.topTenViewedProjectList != null && ! this.topTenViewedProjectList.isEmpty()) {
			this.mostViewedProjectDetailInfo = this.topTenViewedProjectList.get(0);
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
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
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
	
	public ProjectDetailPageViewService getPageViewService() {
		return pageViewService;
	}
	
	public void setPageViewService(ProjectDetailPageViewService pageViewService) {
		this.pageViewService = pageViewService;
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
	
	public List<ProjectDetailPageViewInfo> getTopTenViewedProjectList() {
		return topTenViewedProjectList;
	}
	
	public ProjectDetailPageViewInfo getMostViewedProjectDetailInfo() {
		return mostViewedProjectDetailInfo;
	}

	public List<OhLohProjectEntity> getProjectViewedList() {
		return projectViewedList;
	}
	
}
