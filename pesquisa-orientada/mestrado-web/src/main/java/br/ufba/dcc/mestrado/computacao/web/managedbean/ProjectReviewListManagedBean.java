package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.AjaxBehaviorEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;


@ManagedBean(name="reviewListMB", eager=true)
@ViewScoped
public class ProjectReviewListManagedBean extends AbstractReviewVotingManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6210634487899266367L;
	
	private OhLohProjectEntity project;
	
	private UserEntity user;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService preferenceService;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	private boolean orderByRegisteredAt;
	private boolean orderByReviewRanking;
		
	private Integer startPosition;
	private Integer offset;
	private Integer totalReviews;
	
	private List<PreferenceEntity> reviewList;
		
	public ProjectReviewListManagedBean() {
		this.project = new OhLohProjectEntity();
		this.user = new UserEntity();
		
		this.orderByRegisteredAt = true;
		this.orderByReviewRanking = true;
	}
	
	public Integer getStartPosition() {
		return this.startPosition;
	}
	
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	
	public Integer getOffset() {
		return this.offset;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public Integer getTotalReviews() {
		return this.totalReviews;
	}
	
	public void setTotalReviews(Integer totalReviews) {
		this.totalReviews = totalReviews;
	}
	
	public OhLohProjectEntity getProject() {
		return project;
	}
	
	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}
	
	public UserEntity getUser() {
		return user;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public OverallPreferenceService getPreferenceService() {
		return preferenceService;
	}
	
	public void setPreferenceService(OverallPreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}
	
	public OhLohProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(OhLohProjectService projectService) {
		this.projectService = projectService;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public boolean isOrderByRegisteredAt() {
		return orderByRegisteredAt;
	}

	public void setOrderByRegisteredAt(boolean orderByRegisteredAt) {
		this.orderByRegisteredAt = orderByRegisteredAt;
	}

	public boolean isOrderByReviewRanking() {
		return orderByReviewRanking;
	}

	public void setOrderByReviewRanking(boolean orderByReviewRanking) {
		this.orderByReviewRanking = orderByReviewRanking;
	}

	public List<PreferenceEntity> getReviewList() {
		return this.reviewList;
	}
	
	public void setReviewList(List<PreferenceEntity> reviewList) {
		this.reviewList = reviewList;
	}
	
	protected void searchReviews() {
		final boolean validProject = (getProject() != null && getProject().getId() != null);
		final boolean validUser = (getUser() != null && getUser().getId() != null);
		
		if (validProject || validUser) {
		
			List<PreferenceEntity> data = null;
					
			if (validProject) {
				data = getPreferenceService().findAllLastReviewsByProject(
						getProject().getId(), 
						startPosition, 
						offset,
						isOrderByRegisteredAt(),
						isOrderByReviewRanking());
			} else if (validUser) {
				data = getPreferenceService().findAllLastReviewsByUser(
						getUser().getId(), 
						startPosition, 
						offset,
						isOrderByRegisteredAt(),
						isOrderByReviewRanking());
			}
			
			if (reviewList != null) {
				reviewList.addAll(data);
			} else {
				reviewList = data;
			}

			if (validProject) {
				this.totalReviews = getPreferenceService().countAllLastReviewsByProject(getProject().getId()).intValue();				
			} else if (validUser) {
				this.totalReviews = getPreferenceService().countAllLastReviewsByUser(getUser().getId()).intValue();
			}		
			
			//Se não houver mais o que carregar, atualize logo o startPosition, pro botão do scroll não aparecer a toa
			if (reviewList != null && reviewList.size() >= totalReviews) {
				this.startPosition = reviewList.size();
			}
			
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.reviews");
			
			String summary = bundle.getString("reviews.list.param.required.message");
			
			FacesMessage facesMessage = new FacesMessage(summary);
			
			context.addMessage(null, facesMessage);
		}
	}
	
	public void moreReviews(ActionEvent event) {
		if (reviewList != null) {
			this.startPosition = reviewList.size();
		}
	
		searchReviews();
	}
	
	public void searchReviews(ActionEvent event) {
		searchReviews();
	}
	
	public void initList(ComponentSystemEvent event) {		
		if (this.reviewList == null) {
			this.reviewList = new ArrayList<PreferenceEntity>();
			this.startPosition = 0;
			this.offset = 10;
			this.totalReviews = 0;
			
			final boolean validProject = (getProject() != null && getProject().getId() != null);
			final boolean validUser = (getUser() != null && getUser().getId() != null);
			
			if (validProject || validUser) {
			
				if (validProject) {
					this.project = getProjectService().findById(getProject().getId());
				}

				if (validUser) {
					this.user = getUserService().findById(getUser().getId());
				}
			}
		
			searchReviews();
		}
	}
	
	public void addUsefulVoteToReview(ActionEvent event) {
		super.addUsefulVoteToReview(event);
		
		searchReviews();
	}
	
	public void addUselessVoteToReview(ActionEvent event) {
		super.addUselessVoteToReview(event);
		
		searchReviews();
	}

}
