package br.ufba.dcc.mestrado.computacao.web.managedbean;

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
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;
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
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
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

	public void searchReviews(ActionEvent event) {
		Integer startPosition = loadStartPositionFromParams();
		Integer pageSize = getDataModel().getPageSize();
		
		getDataModel().load(startPosition, pageSize);
	}
	
	
	
	public void initList(ComponentSystemEvent event) {
		final boolean validProject = (getProject() != null && getProject().getId() != null);
		final boolean validUser = (getUser() != null && getUser().getId() != null);
		
		if (validProject || validUser) {
			
			if (validProject) {
				this.project = getProjectService().findById(getProject().getId());
			}
			
			if (validUser) {
				this.user = getUserService().findById(getUser().getId());
			}
			
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
					
					
					if (this.getWrappedData() != null) {
						List<PreferenceEntity> fullData = (List<PreferenceEntity>) this.getWrappedData();
						fullData.addAll(data);
						data = fullData;
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
