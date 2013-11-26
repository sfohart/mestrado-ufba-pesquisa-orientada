package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.web.pagination.LazyLoadingDataModel;
import br.ufba.dcc.mestrado.computacao.web.pagination.PageList;

@ManagedBean(name="projectReviewMB", eager=true)
@ViewScoped
public class ProjectReviewManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3583610472886856317L;
	
	private OhLohProjectEntity project;
	private UserEntity account;
	
	private PreferenceEntity preference;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService preferenceService;
	
	@ManagedProperty("#{recommenderCriteriumService}")
	private RecommenderCriteriumService criteriumService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	private LazyLoadingDataModel<Long, PreferenceEntity> dataModel;
	private PageList pageList;
	
	public ProjectReviewManagedBean() {
		this.project = new OhLohProjectEntity();
		
		this.dataModel = new LazyLoadingDataModel<Long, PreferenceEntity>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void load(int first, int pageSize) {
				
				List<PreferenceEntity> data = getPreferenceService().findAllLastByProject(getProject(), first, pageSize);
				this.setWrappedData(data);
				
				Integer totalRecords = getPreferenceService().countAllLastByProject(getProject()).intValue();				
				Integer currentPage = (first / pageSize) + 1;
								
				PageList pageList = new PageList(currentPage, totalRecords, pageSize);
				setPageList(pageList);
			}
		};
	}
	
	public OhLohProjectEntity getProject() {
		return project;
	}
	
	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}
	
	public UserEntity getAccount() {
		return account;
	}
	
	public void setAccount(UserEntity account) {
		this.account = account;
	}

	public OhLohProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(OhLohProjectService projectService) {
		this.projectService = projectService;
	}
	
	public RecommenderCriteriumService getCriteriumService() {
		return criteriumService;
	}

	public void setCriteriumService(RecommenderCriteriumService criteriumService) {
		this.criteriumService = criteriumService;
	}
	
	public OverallPreferenceService getPreferenceService() {
		return preferenceService;
	}
	
	public void setPreferenceService(OverallPreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public PreferenceEntity getPreference() {
		return preference;
	}
	
	public void setPreference(PreferenceEntity preference) {
		this.preference = preference;
	}

	public void initNew(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			this.account = getUserDetailsService().loadFullLoggedUser();
			
			List<RecommenderCriteriumEntity> criteriumList = getCriteriumService().findAll();
			if (criteriumList != null) {
				this.preference = new PreferenceEntity();
				
				this.preference.setUser(getAccount());
				this.preference.setUserId(getAccount().getId());
				
				this.preference.setProject(getProject());
				this.preference.setProjectId(getProject().getId());
				
				PreferenceReviewEntity preferenceReview = new PreferenceReviewEntity();
				preferenceReview.setPreference(this.preference);
				
				this.preference.setPreferenceReview(preferenceReview);
				
				List<PreferenceEntryEntity> preferenceEntryEntityList = new ArrayList<PreferenceEntryEntity>();
				for (RecommenderCriteriumEntity criterium : criteriumList) {
					PreferenceEntryEntity preferenceEntry = new PreferenceEntryEntity();
					preferenceEntry.setCriterium(criterium);
					preferenceEntry.setCriteriumId(criterium.getId());
					preferenceEntry.setPreference(this.preference);
					
					preferenceEntryEntityList.add(preferenceEntry);
				}
				
				this.preference.setPreferenceEntryList(preferenceEntryEntityList);
			}
		}
	}
	
	public void initList(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			
			Integer first = 0;
			Integer pageSize = 10;
			
			getDataModel().load(first, pageSize);
		}
	}
	
	public LazyLoadingDataModel<Long, PreferenceEntity> getDataModel() {
		return dataModel;
	}
	
	public PageList getPageList() {
		return pageList;
	}
	
	public void setPageList(PageList pageList) {
		this.pageList = pageList;
	}
	
	public void searchReviews(ActionEvent event) {
		Map<String, String> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
		
		String pageParam = params.get("page");
		
		Integer startPosition = null;
		Integer pageSize = getDataModel().getPageSize();
		
		if (pageParam != null){
			Integer page = Integer.parseInt(pageParam);
			if (page != null && page > 0) {
				startPosition = getDataModel().getPageSize() * (page - 1);
			}
		} else {
			startPosition = 0;
		}
		
		getDataModel().load(startPosition, pageSize);
	}
	
	public String saveReview() {
		
		try {
			getPreferenceService().save(getPreference());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "projectReviews.jsf?faces-redirect=true&includeViewParams=true&projectId=" + project.getId();
	}

}
