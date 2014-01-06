package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.web.pagination.LazyLoadingDataModel;
import br.ufba.dcc.mestrado.computacao.web.pagination.PageList;

@ManagedBean(name="reviewListMB", eager=true)
@ViewScoped
public class ProjectReviewListManagedBean extends AbstractListingManagedBean<Long, PreferenceEntity> {

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
	
	private boolean orderByRegisteredAt;
	private boolean orderByReviewRanking;
	
	public ProjectReviewListManagedBean() {
		this.project = new OhLohProjectEntity();
		this.user = new UserEntity();
		
		this.orderByRegisteredAt = true;
		this.orderByReviewRanking = true;
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

	public void searchReviews(ActionEvent event) {
		Integer startPosition = loadStartPositionFromParams();
		Integer pageSize = getDataModel().getPageSize();
		
		getDataModel().load(startPosition, pageSize);
	}
	
	public void addUsefulVoteToReview(AjaxBehaviorEvent event) {
		PreferenceEntity preference = (PreferenceEntity)
				event.getComponent().getAttributes().get("preference");
		
		if (preference != null && preference.getPreferenceReview() != null) {
			UserEntity user = getUserDetailsService().loadFullLoggedUser();
			
			if (preference.getPreferenceReview().getUsefulList() == null) {
				preference.getPreferenceReview().setUsefulList(new HashSet<UserEntity>());
			}
			
			if (! isDulicatedVoteMessage(event, preference)) {
				preference.getPreferenceReview().getUsefulList().add(user);
				
				try {
					getPreferenceService().save(preference);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				
			}
		}
	}
	
	public void addUselessVoteToReview(AjaxBehaviorEvent event) {
		PreferenceEntity preference = (PreferenceEntity)
				event.getComponent().getAttributes().get("preference");
		
		if (preference != null && preference.getPreferenceReview() != null) {
			UserEntity user = getUserDetailsService().loadFullLoggedUser();
			
			if (preference.getPreferenceReview().getUselessList() == null) {
				preference.getPreferenceReview().setUselessList(new HashSet<UserEntity>());
			}
			
			if (! isDulicatedVoteMessage(event, preference)) {
				preference.getPreferenceReview().getUselessList().add(user);
				
				try {
					getPreferenceService().save(preference);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				
			}
		}
	}

	protected boolean isDulicatedVoteMessage(AjaxBehaviorEvent event, PreferenceEntity preference) {
		boolean duplicated = false;
		
		UserEntity user = getUserDetailsService().loadFullLoggedUser();
		
		if (preference.getPreferenceReview().getUselessList().contains(user)) {
			duplicated = true;
		} else if (preference.getPreferenceReview().getUsefulList().contains(user)) {
			duplicated = true;
		}
		
		if (duplicated) {
			ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.reviews");
			
			String message = bundle.getString("reviews.vote.duplicated.message");
			FacesMessage facesMessage = new FacesMessage(message);
			
			FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), facesMessage);
		}
		
		return duplicated;
	}
	
	public void initList(ComponentSystemEvent event) {
		final boolean validProject = (getProject() != null && getProject().getId() != null);
		final boolean validUser = (getUser() != null && getUser().getId() != null);
		
		if (validProject || validUser) {
			
			this.dataModel = new LazyLoadingDataModel<Long, PreferenceEntity>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void load(int first, int pageSize) {
					
					List<PreferenceEntity> data = null;
					
					if (validProject) {
						data = getPreferenceService().findAllLastReviewsByProject(
								getProject().getId(), 
								first, 
								pageSize,
								isOrderByRegisteredAt(),
								isOrderByReviewRanking());
					} else if (validUser) {
						data = getPreferenceService().findAllLastReviewsByUser(
								getUser().getId(), 
								first, 
								pageSize,
								isOrderByRegisteredAt(),
								isOrderByReviewRanking());
					}
					
					
					
					this.setWrappedData(data);
					
					Integer totalRecords = null;
					
					if (validProject) {
						totalRecords = getPreferenceService().countAllLastReviewsByProject(getProject().getId()).intValue();				
					} else if (validUser) {
						totalRecords = getPreferenceService().countAllLastReviewsByUser(getUser().getId()).intValue();
					}
					
					Integer currentPage = (first / pageSize) + 1;
									
					PageList pageList = new PageList(currentPage, totalRecords, pageSize);
					setPageList(pageList);
				}
			};
			
			Integer first = 0;
			Integer pageSize = 10;
			
			getDataModel().load(first, pageSize);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.reviews");
			
			String summary = bundle.getString("reviews.list.param.required.message");
			
			FacesMessage facesMessage = new FacesMessage(summary);
			
			context.addMessage(null, facesMessage);
		}
		
	}

}
