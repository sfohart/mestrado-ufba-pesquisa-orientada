package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.ProjectPreferenceInfo;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
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
	
	@ManagedProperty("#{projectService}")
	private ProjectService projectService;
	
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
	private List<OhLohProjectEntity> recommendedProjectList;
	
	private ProjectPreferenceInfo mostReviewedProjectPreferenceInfo;
	private ProjectDetailPageViewInfo mostViewedProjectDetailInfo;
		
	public IndexManagedBean() {
	}

	public void init(ComponentSystemEvent event) {
		this.topTenReviewedProjectList = getOverallPreferenceService().findAllProjectPreferenceInfo(0, 10);
		this.topTenViewedProjectList = getPageViewService().findAllProjectDetailPageViewInfo(0, 10);
		
		
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
		try {
			//limpa lista atual de projetos recomendados
			this.recommendedProjectList = new ArrayList<>();
			final UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
			
			if (currentUser != null) {
				GenericBooleanPrefItemBasedRecommender recommender = getPageViewService().buildProjectRecommender();
				
				//aplicando filtro de tags no recomendador
				List<RecommendedItem> recommendedItemList = recommender.recommend(currentUser.getId(), 6, new IDRescorer() {
					
					@Override
					public double rescore(long id, double originalScore) {
						return originalScore;
					}
					
					@Override
					public boolean isFiltered(long id) {
						OhLohProjectEntity project = getProjectService().findById(id);
						if (currentUser.getInterestTags() != null && ! currentUser.getInterestTags().isEmpty()) {
							for (OhLohTagEntity tag : currentUser.getInterestTags()) {
								if (project.getTags().contains(tag)) {
									return false;
								}
							}
							
							return true;
						} else {
							return false;
						}
					}
				});
				
				if (recommendedItemList != null) {
					for (RecommendedItem recommendedItem : recommendedItemList) {
						OhLohProjectEntity recommendedProject = getProjectService().findById(recommendedItem.getItemID());
						this.recommendedProjectList.add(recommendedProject);
					}
				}
			}
		} catch (TasteException e1) {
			e1.printStackTrace();
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
		return getProjectService().countAll();
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
	
	public List<OhLohProjectEntity> getRecommendedProjectList() {
		return recommendedProjectList;
	}
}
