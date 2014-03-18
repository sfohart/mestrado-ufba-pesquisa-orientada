package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ActionEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.CriteriumPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;

@ManagedBean(name = "summaryReviewsMB")
@ViewScoped
public class ProjectSummaryReviewsManagedBean extends AbstractReviewVotingManagedBean {

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
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	private Long overallPreferenceCount;
	private Long reviewsCount;
	
	private PreferenceEntity averagePreference;
	
	private PreferenceEntity currentUserPreference;
	
	private PreferenceReviewEntity mostHelpfulFavorableReview;
	private PreferenceReviewEntity mostHelpfulCriticalReview;
	
	private List<PreferenceEntity> mostRecentReviewList;
	private List<PreferenceEntity> mostHelpfulReviewList;
	
	
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
	
	public List<PreferenceEntity> getMostHelpfulReviewList() {
		return mostHelpfulReviewList;
	}
	
	public List<PreferenceEntity> getMostRecentReviewList() {
		return mostRecentReviewList;
	}

	protected void findTopFiveReviews() {
		this.mostHelpfulReviewList 	= getOverallPreferenceService().findAllLastReviewsByProject(getProject().getId(), 0, 5, false, true);
		this.mostRecentReviewList 	= getOverallPreferenceService().findAllLastReviewsByProject(getProject().getId(), 0, 5, true, false);
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			this.overallPreferenceCount = getOverallPreferenceService().countAllLastByProject(getProject().getId());
			this.reviewsCount = getOverallPreferenceService().countAllLastReviewsByProject(getProject().getId());
			
			findTopFiveReviews();
			
			//calculando avaliação média do projeto
			this.averagePreference = getOverallPreferenceService().averagePreferenceByProject(getProject().getId());
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (authentication != null) {
				if (! "anonymousUser".equals(authentication.getName())) {
					UserEntity userEntity = getUserService().findByLogin(authentication.getName());
					if (userEntity == null) { 
						userEntity = getUserService().findBySocialLogin(authentication.getName());
					}
					
					if (userEntity != null) {
						this.currentUserPreference = getOverallPreferenceService().findLastByProjectAndUser(getProject().getId(), userEntity.getId());
						this.mostHelpfulReviewList.remove(currentUserPreference);
						this.mostRecentReviewList.remove(currentUserPreference);
					}
				}
			}
		}
	}
	
	public void addUsefulVoteToReview(ActionEvent event) {
		super.addUsefulVoteToReview(event);
		
		findTopFiveReviews();
	}
	
	public void addUselessVoteToReview(ActionEvent event) {
		super.addUselessVoteToReview(event);
		
		findTopFiveReviews();
	}

}
