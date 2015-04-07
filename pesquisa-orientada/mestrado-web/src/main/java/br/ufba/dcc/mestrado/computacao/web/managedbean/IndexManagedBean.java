package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.dto.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.dto.pageview.ProjectReviewsInfo;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

@ManagedBean(name="indexMB")
@ViewScoped
public class IndexManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1409944916651513725L;
	
	@ManagedProperty("#{projectService}")
	private ProjectService projectService;
	
	@ManagedProperty("#{mahoutColaborativeFilteringService}")
	private BaseColaborativeFilteringService colaborativeFilteringService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{overallRatingService}")
	private OverallRatingService overallRatingService;
	
	@ManagedProperty("#{recommenderCriteriumService}")
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@ManagedProperty("#{projectDetailPageViewService}")
	private ProjectDetailPageViewService pageViewService;
	
	private List<ProjectReviewsInfo> topTenReviewedProjectList;
	private List<ProjectDetailPageViewInfo> topTenViewedProjectList;
	
	private List<OpenHubProjectEntity> projectViewedList;
	private List<OpenHubProjectEntity> recommendedProjectList;
	
	private ProjectReviewsInfo mostReviewedProjectPreferenceInfo;
	private ProjectDetailPageViewInfo mostViewedProjectDetailInfo;
		
	public IndexManagedBean() {
	}

	public void init(ComponentSystemEvent event) {
		findTopTenReviewedProjectList();
		findTopTenViewedProjectList();		
		
		findUserRecentlyViewedProjectList();
		
		if (this.topTenReviewedProjectList != null && ! this.topTenReviewedProjectList.isEmpty()) {
			this.mostReviewedProjectPreferenceInfo = this.topTenReviewedProjectList.get(0);
		}
		
		if (this.topTenViewedProjectList != null && ! this.topTenViewedProjectList.isEmpty()) {
			this.mostViewedProjectDetailInfo = this.topTenViewedProjectList.get(0);
		}
		
		findUserRecentlyViewedProjectList();
		findRecommendedProjectList();
	}

	protected void findTopTenViewedProjectList() {
		
		List<ImmutablePair<OpenHubProjectEntity, Long>> viewedProjectData = getPageViewService().findAllProjectDetailViewsCount(0, 10);
		this.topTenViewedProjectList = new ArrayList<>();
		
		if (viewedProjectData != null && ! viewedProjectData.isEmpty()) {
			for (ImmutablePair<OpenHubProjectEntity, Long> pair : viewedProjectData) {
				ProjectDetailPageViewInfo info = new ProjectDetailPageViewInfo();
				info.setProject(pair.getLeft());
				info.setPageViewCount(pair.getRight());
				
				topTenViewedProjectList.add(info);
			}
		}
	}

	protected void findTopTenReviewedProjectList() {
		List<ImmutablePair<OpenHubProjectEntity, Long>> reviewedProjectData = getOverallRatingService().findRatingCountByProject(0, 10);
		
		this.topTenReviewedProjectList = new ArrayList<>();
		
		if (reviewedProjectData != null && ! reviewedProjectData.isEmpty()) {
			for (ImmutablePair<OpenHubProjectEntity, Long> pair : reviewedProjectData) {
				ProjectReviewsInfo info = new ProjectReviewsInfo();
				
				info.setProject(pair.getLeft());
				info.setReviewsCount(pair.getRight());
				
				this.topTenReviewedProjectList.add(info);
			}
		}
	}

	private void findUserRecentlyViewedProjectList() {
		this.projectViewedList = new ArrayList<>();
		UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		
		if (currentUser != null) {
			this.projectViewedList = getPageViewService().findAllProjectRecentlyViewed(
					getUserDetailsService().loadFullLoggedUser(), 
					0, 
					6);
		}
	}
	
	protected void findRecommendedProjectList() {
		this.recommendedProjectList = new ArrayList<>();
		final UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		if (currentUser != null && currentUser.getId() != null) {
			this.recommendedProjectList = getColaborativeFilteringService().recommendViewedProjectsByUser(currentUser.getId(), 6, true);		
		}
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
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
	
	public OverallRatingService getOverallRatingService() {
		return overallRatingService;
	}

	public void setOverallRatingService(OverallRatingService overallRatingService) {
		this.overallRatingService = overallRatingService;
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
	
	
	public BaseColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public void setColaborativeFilteringService(
			BaseColaborativeFilteringService colaborativeFilteringService) {
		this.colaborativeFilteringService = colaborativeFilteringService;
	}

	public Long getProjectCount() {
		return getProjectService().countAll();
	}
	
	public Long getUserCount() {
		return getUserService().countAll();
	}
	
	public Long getPreferencesCount() {
		return getOverallRatingService().countAllLast();
	}
	
	public Long getRecommenderCriteriumCount() {
		return  getRecommenderCriteriumService().countAll();
	}
	
	public List<ProjectReviewsInfo> getTopTenReviewedProjectList() {
		return topTenReviewedProjectList;
	}
	
	public ProjectReviewsInfo getMostReviewedProjectPreferenceInfo() {
		return mostReviewedProjectPreferenceInfo;
	}
	
	public List<ProjectDetailPageViewInfo> getTopTenViewedProjectList() {
		return topTenViewedProjectList;
	}
	
	public ProjectDetailPageViewInfo getMostViewedProjectDetailInfo() {
		return mostViewedProjectDetailInfo;
	}

	public List<OpenHubProjectEntity> getProjectViewedList() {
		return projectViewedList;
	}
	
	public List<OpenHubProjectEntity> getRecommendedProjectList() {
		return recommendedProjectList;
	}
}
