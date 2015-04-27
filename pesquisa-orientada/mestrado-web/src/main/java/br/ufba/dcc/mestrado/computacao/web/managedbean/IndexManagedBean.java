package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.dto.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.dto.pageview.ProjectReviewsInfo;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommendationEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.UserRecommendationService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.ColaborativeFilteringService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.MultiCriteriaRecommenderService;
import br.ufba.dcc.mestrado.computacao.web.managedbean.project.RecommendedProjectManagedBean;

@ManagedBean(name="indexMB")
@SessionScoped
public class IndexManagedBean extends RecommendedProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1409944916651513725L;
	
	
	@ManagedProperty("#{mahoutColaborativeFilteringService}")
	private ColaborativeFilteringService colaborativeFilteringService;
	
	@ManagedProperty("#{multiCriteriaListBasedRecommenderService}")
	private MultiCriteriaRecommenderService multiCriteriaRecommenderService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{overallRatingService}")
	private OverallRatingService overallRatingService;
	
	@ManagedProperty("#{userRecommendationService}")
	private UserRecommendationService userRecommendationService;
	
	@ManagedProperty("#{recommenderCriteriumService}")
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@ManagedProperty("#{projectDetailPageViewService}")
	private ProjectDetailPageViewService pageViewService;
	
	private List<ProjectReviewsInfo> topTenReviewedProjectList;
	private List<ProjectDetailPageViewInfo> topTenViewedProjectList;
	
	private List<OpenHubProjectEntity> projectViewedList;
	
	private UserRecommendationEntity colaborativeFilteringRecommendation;	
	private UserRecommendationEntity multiCriteriaRecommendation;
	
	private ProjectReviewsInfo mostReviewedProjectPreferenceInfo;
	private ProjectDetailPageViewInfo mostViewedProjectDetailInfo;
		
	public IndexManagedBean() {
		super();
	}

	@Override
	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (topTenReviewedProjectList == null) {
			findTopTenReviewedProjectList();
		}
		
		if (topTenViewedProjectList == null) {
			findTopTenViewedProjectList();		
		}
		
		if (this.topTenReviewedProjectList != null && ! this.topTenReviewedProjectList.isEmpty()) {
			this.mostReviewedProjectPreferenceInfo = this.topTenReviewedProjectList.get(0);
		}
		
		if (this.topTenViewedProjectList != null && ! this.topTenViewedProjectList.isEmpty()) {
			this.mostViewedProjectDetailInfo = this.topTenViewedProjectList.get(0);
		}
		
		if (projectViewedList == null) {
			findUserRecentlyViewedProjectList();
		}
		
		if (colaborativeFilteringRecommendation == null) {
			findColaborativeFilteringRecommendedProjects();
		}
		
		if (multiCriteriaRecommendation == null) {
			findMultiCriteriaRecommendedProjects();
		}
	}

	protected void findTopTenViewedProjectList() {
		
		List<ImmutablePair<OpenHubProjectEntity, Long>> viewedProjectData = getPageViewService().groupProjectDetailViewsCount(0, 10);
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
		UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		
		if (currentUser != null) {
			this.projectViewedList = getPageViewService().findAllProjectRecentlyViewedByUser(
					getUserDetailsService().loadFullLoggedUser(), 
					0, 
					6);
		}
	}
	
	protected RecommendationEvaluationEntity buildRecommendationEvaluation(Long selectedProjectId) {
		RecommendationEvaluationEntity recommendationEvaluation = null;
		
		UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		
		if (colaborativeFilteringRecommendation != null
				&& colaborativeFilteringRecommendation.getRecommendedProjects() != null) {
			for (OpenHubProjectEntity project : colaborativeFilteringRecommendation.getRecommendedProjects()) {
				if (project.getId().equals(selectedProjectId)) {
					recommendationEvaluation = new RecommendationEvaluationEntity();
					recommendationEvaluation.setSelectedProject(project);
					recommendationEvaluation.setUserRecommendation(colaborativeFilteringRecommendation);
					recommendationEvaluation.getUserRecommendation().setUser(currentUser);
					recommendationEvaluation.setEvaluationDate(new Timestamp(System.currentTimeMillis()));
					break;
				}
			}
		}
		
		if (recommendationEvaluation == null
				&& multiCriteriaRecommendation != null
				&& multiCriteriaRecommendation.getRecommendedProjects() != null) {
			for (OpenHubProjectEntity project : multiCriteriaRecommendation.getRecommendedProjects()) {
				if (project.getId().equals(selectedProjectId)) {
					recommendationEvaluation = new RecommendationEvaluationEntity();
					recommendationEvaluation.setSelectedProject(project);
					recommendationEvaluation.setUserRecommendation(multiCriteriaRecommendation);
					recommendationEvaluation.getUserRecommendation().setUser(currentUser);
					recommendationEvaluation.setEvaluationDate(new Timestamp(System.currentTimeMillis()));
					break;
				}
			}
			
		}
	
		return recommendationEvaluation;
		
	}
	
	protected void findColaborativeFilteringRecommendedProjects() {
		final UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		if (currentUser != null && currentUser.getId() != null) {
			this.colaborativeFilteringRecommendation = userRecommendationService.findLastUserRecommendation(currentUser, RecommendationTypeEnum.COLABORATIVE_FILTERING_USER_BASED_RECOMMENDATION);
		}
	}

	
	protected void findMultiCriteriaRecommendedProjects() {
		final UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		if (currentUser != null && currentUser.getId() != null) {
			this.multiCriteriaRecommendation = userRecommendationService.findLastUserRecommendation(currentUser, RecommendationTypeEnum.MULTICRITERIA_LIST_BASED_RECOMMENDATION);			
		}
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
	
	
	public ColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public void setColaborativeFilteringService(
			ColaborativeFilteringService colaborativeFilteringService) {
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
	
	public UserRecommendationEntity getColaborativeFilteringRecommendation() {
		return colaborativeFilteringRecommendation;
	}
	
	public MultiCriteriaRecommenderService getMultiCriteriaRecommenderService() {
		return multiCriteriaRecommenderService;
	}

	public void setMultiCriteriaRecommenderService(
			MultiCriteriaRecommenderService multiCriteriaRecommenderService) {
		this.multiCriteriaRecommenderService = multiCriteriaRecommenderService;
	}

	public UserRecommendationService getUserRecommendationService() {
		return userRecommendationService;
	}

	public void setUserRecommendationService(
			UserRecommendationService userRecommendationService) {
		this.userRecommendationService = userRecommendationService;
	}

	public UserRecommendationEntity getMultiCriteriaRecommendation() {
		return multiCriteriaRecommendation;
	}

	public void setMultiCriteriaRecommendation(
			UserRecommendationEntity multiCriteriaRecommendation) {
		this.multiCriteriaRecommendation = multiCriteriaRecommendation;
	}

	
}
