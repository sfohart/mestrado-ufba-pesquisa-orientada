package br.ufba.dcc.mestrado.computacao.web.managedbean.review;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.PreferenceReviewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.ocpsoft.pretty.faces.annotation.URLQueryParameter;

@ManagedBean(name="reviewListMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="reviewListByProjectMapping",
				beanName="reviewListMB", 
				pattern="/reviews/project/#{ /[0-9]+/ projectId}",
				viewId="/reviews/reviewsList.jsf"),
		@URLMapping(
				id="reviewListByUserMapping",
				beanName="reviewListMB", 
				pattern="/reviews/user/#{ /[0-9]+/ userId}",
				viewId="/reviews/reviewsList.jsf")
	})
public class ProjectReviewListManagedBean extends AbstractReviewVotingManagedBean implements ReviewVoting {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6210634487899266367L;
	
	private OhLohProjectEntity project;
	
	private UserEntity user;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{preferenceReviewService}")
	private PreferenceReviewService preferenceReviewService;
	
	@ManagedProperty("#{projectService}")
	private ProjectService projectService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	@URLQueryParameter(mappingId="reviewListByProjectMapping", value="orderByRegisteredAt")
	private boolean orderByRegisteredAt;
	
	@URLQueryParameter(mappingId="reviewListByProjectMapping", value="orderByReviewRanking")
	private boolean orderByReviewRanking;
		
	private Integer startPosition;
	private Integer offset;
	private Integer totalReviews;
	
	private List<PreferenceReviewEntity> reviewList;
		
	public ProjectReviewListManagedBean() {
		this.project = new OhLohProjectEntity();
		this.user = new UserEntity();
		
		this.orderByRegisteredAt = false;
		this.orderByReviewRanking = false;
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
	
	public PreferenceReviewService getPreferenceReviewService() {
		return preferenceReviewService;
	}

	public void setPreferenceReviewService(
			PreferenceReviewService preferenceReviewService) {
		this.preferenceReviewService = preferenceReviewService;
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

	public List<PreferenceReviewEntity> getReviewList() {
		return this.reviewList;
	}
	
	public void setReviewList(List<PreferenceReviewEntity> reviewList) {
		this.reviewList = reviewList;
	}
	
	protected void searchReviews() {
		final boolean validProject = (getProject() != null && getProject().getId() != null);
		final boolean validUser = (getUser() != null && getUser().getId() != null);
		
		if (validProject || validUser) {
			
			List<PreferenceReviewEntity> data = null;
					
			if (validProject) {
				data = getPreferenceReviewService().findAllLastReviewsByProject(
						getProject().getId(), 
						startPosition, 
						offset,
						isOrderByRegisteredAt(),
						isOrderByReviewRanking());
			} else if (validUser) {
				data = getPreferenceReviewService().findAllLastReviewsByUser(
						getUser().getId(), 
						startPosition, 
						offset,
						true,
						false);
			}
			
			if (data != null) {
				for (PreferenceReviewEntity preference : data) {
					if (getReviewList().contains(preference)) {
						getReviewList().remove(preference);
					}
					
					getReviewList().add(preference);
				}
			}

			if (validProject) {
				this.totalReviews = getPreferenceReviewService().countAllLastReviewsByProject(getProject().getId()).intValue();				
			} else if (validUser) {
				this.totalReviews = getPreferenceReviewService().countAllLastReviewsByUser(getUser().getId()).intValue();
			}		
			
			//Se não houver mais o que carregar, atualize logo o startPosition, pro botão do scroll não aparecer a toa
			if (reviewList != null && reviewList.size() >= totalReviews) {
				startPosition = reviewList.size();
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
			startPosition = reviewList.size();
		}
	
		searchReviews();
	}
	
	public void searchReviews(ActionEvent event) {
		searchReviews();
	}
	
	public void initList(ComponentSystemEvent event) {		
		if (this.reviewList == null) {
			this.reviewList = new ArrayList<PreferenceReviewEntity>();
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
	
	protected void updatePreferenceInList(PreferenceReviewEntity preference) {
		//atualizando preferência
		
		Integer index = getReviewList().indexOf(preference);
		if (index >= 0) {
			getReviewList().set(index, preference);
		}
	}
	
	public void watchLikeReview(ActionEvent event) {
		PreferenceReviewEntity preference = super.addUsefulVoteToReview(event);
		
		updatePreferenceInList(preference);
	}
	
	public void watchDislikeReview(ActionEvent event) {
		PreferenceReviewEntity preference = super.addUselessVoteToReview(event);
		
		updatePreferenceInList(preference);
	}

}
