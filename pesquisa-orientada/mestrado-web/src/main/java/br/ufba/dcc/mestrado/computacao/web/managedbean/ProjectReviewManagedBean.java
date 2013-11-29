package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
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
public class ProjectReviewManagedBean extends AbstractListingManagedBean<Long, PreferenceEntity> {
	
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
	
	public ProjectReviewManagedBean() {
		this.project = new OhLohProjectEntity();
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
			
			this.dataModel = new LazyLoadingDataModel<Long, PreferenceEntity>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void load(int first, int pageSize) {
					
					List<PreferenceEntity> data = getPreferenceService().findAllLastReviewsByProject(getProject(), first, pageSize);
					this.setWrappedData(data);
					
					Integer totalRecords = getPreferenceService().countAllLastReviewsByProject(getProject()).intValue();				
					Integer currentPage = (first / pageSize) + 1;
									
					PageList pageList = new PageList(currentPage, totalRecords, pageSize);
					setPageList(pageList);
				}
			};
			
			Integer first = 0;
			Integer pageSize = 10;
			
			getDataModel().load(first, pageSize);
		}
	}
	
	public LazyLoadingDataModel<Long, PreferenceEntity> getDataModel() {
		return dataModel;
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
			
			preference.getPreferenceReview().getUsefulList().add(user);
			
			try {
				getPreferenceService().save(preference);
			} catch (Exception e) {
				e.printStackTrace();
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
			
			preference.getPreferenceReview().getUselessList().add(user);
			
			try {
				getPreferenceService().save(preference);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
