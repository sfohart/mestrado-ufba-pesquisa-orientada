package br.ufba.dcc.mestrado.computacao.web.managedbean.review;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.PreferenceReviewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "summaryReviewsMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="summaryReviewsMapping",
				beanName="summaryReviewsMB", 
				pattern="/summary/#{ /[0-9]+/ projectId}/",
				viewId="/summary/summaryReviews.jsf")	
	})
public class ProjectSummaryReviewsManagedBean extends AbstractReviewVotingManagedBean implements ReviewVoting  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8863511598108591199L;
	
	private OpenHubProjectEntity project;
	
	@ManagedProperty("#{projectService}")
	private ProjectService projectService;
	
	@ManagedProperty("#{overallRatingService}")
	private OverallRatingService overallRatingService;
	
	@ManagedProperty("#{preferenceReviewService}")
	private PreferenceReviewService preferenceReviewService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	private Long overallPreferenceCount;
	private Long reviewsCount;
	
	private PreferenceEntity averagePreference;
	
	private PreferenceEntity currentUserPreference;
	
	private PreferenceReviewEntity mostHelpfulFavorableReview;
	private PreferenceReviewEntity mostHelpfulCriticalReview;
	
	private List<PreferenceReviewEntity> mostRecentReviewList;
	private List<PreferenceReviewEntity> mostHelpfulReviewList;
	
	
	public ProjectSummaryReviewsManagedBean() {
		this.project = new OpenHubProjectEntity();
		this.averagePreference = new PreferenceEntity();
	}
	
	public OpenHubProjectEntity getProject() {
		return project;
	}
	
	public void setProject(OpenHubProjectEntity project) {
		this.project = project;
	}
	
	public ProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public OverallRatingService getOverallRatingService() {
		return overallRatingService;
	}

	public void setOverallRatingService(OverallRatingService overallRatingService) {
		this.overallRatingService = overallRatingService;
	}

	public PreferenceReviewService getPreferenceReviewService() {
		return preferenceReviewService;
	}

	public void setPreferenceReviewService(
			PreferenceReviewService preferenceReviewService) {
		this.preferenceReviewService = preferenceReviewService;
	}

	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	
	public PreferenceEntity getCurrentUserPreference() {
		return currentUserPreference;
	}
	
	public PreferenceReviewEntity getMostHelpfulFavorableReview() {
		return mostHelpfulFavorableReview;
	}

	public PreferenceReviewEntity getMostHelpfulCriticalReview() {
		return mostHelpfulCriticalReview;
	}
	
	public List<PreferenceReviewEntity> getMostHelpfulReviewList() {
		return mostHelpfulReviewList;
	}
	
	public List<PreferenceReviewEntity> getMostRecentReviewList() {
		return mostRecentReviewList;
	}

	protected void findTopFiveReviews() {
		this.mostHelpfulReviewList 	= getPreferenceReviewService().findAllLastReviewsByProject(getProject().getId(), 0, 5, false, true);
		this.mostRecentReviewList 	= getPreferenceReviewService().findAllLastReviewsByProject(getProject().getId(), 0, 5, true, false);
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			this.overallPreferenceCount = getOverallRatingService().countAllLastPreferenceByProject(getProject().getId());
			this.reviewsCount = getPreferenceReviewService().countAllLastReviewsByProject(getProject().getId());
			
			findTopFiveReviews();
			
			//calculando avaliação média do projeto
			this.averagePreference = getOverallRatingService().averagePreferenceByItem(getProject().getId());
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (authentication != null) {
				if (! "anonymousUser".equals(authentication.getName())) {
					UserEntity userEntity = getUserService().findByLogin(authentication.getName());
					if (userEntity == null) { 
						userEntity = getUserService().findBySocialLogin(authentication.getName());
					}
					
					if (userEntity != null) {
						this.currentUserPreference = getOverallRatingService().findLastOverallPreferenceByUserAndItem(userEntity.getId(), getProject().getId());
						this.mostHelpfulReviewList.remove(currentUserPreference);
						this.mostRecentReviewList.remove(currentUserPreference);
					}
				}
			}
		}
	}
	
	public void watchLikeReview(ActionEvent event) {
		super.addUsefulVoteToReview(event);
		
		findTopFiveReviews();
	}
	
	public void watchDislikeReview(ActionEvent event) {
		super.addUselessVoteToReview(event);
		
		findTopFiveReviews();
	}

}
