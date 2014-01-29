package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.hdfs.server.namenode.FileChecksumServlets.GetServlet;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.factoid.OhLohFactoidEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.service.base.CriteriumPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohEnlistmentService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLinkService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.service.basic.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@ManagedBean(name="projectDetailMB", eager=true)
@ViewScoped
public class ProjectDetailManageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3959153545005214806L;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	@ManagedProperty("#{ohLohEnlistmentService}")
	private OhLohEnlistmentService enlistmentService;
	
	@ManagedProperty("#{ohLohAnalysisService}")
	private OhLohAnalysisService analysisService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService overallPreferenceService;
	
	@ManagedProperty("#{criteriumPreferenceService}")
	private CriteriumPreferenceService criteriumPreferenceService;
	
	@ManagedProperty("#{ohLohLinkService}")
	private OhLohLinkService linkService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
		
	@ManagedProperty("#{projectDetailPageViewService}")
	private ProjectDetailPageViewService pageViewService;
	
	@ManagedProperty("#{searchService}")
	private SearchService searchService;
	
	private OhLohProjectEntity project;
	private List<OhLohProjectEntity> alsoViewedProjectList;
	private List<OhLohProjectEntity> relatedProjectEntities;
	
	
	private List<OhLohFactoidEntity> factoidList;
	private OhLohAnalysisLanguagesEntity analysisLanguages;
		
	private List<OhLohEnlistmentEntity> enlistmentList;
	private Long enlistmentCount;
	
	private List<OhLohLinkEntity> linkList;
	private Long linkCount;
	
	private Long overallPreferenceCount;
		
	private PreferenceEntity averagePreference;
	private PreferenceEntity userPreference;
	
	private String[] projectDescritionParagraphs;
	
	public ProjectDetailManageBean() {
		this.project = new OhLohProjectEntity();
		this.averagePreference = new PreferenceEntity();
	}
	
	protected void savePageViewInfo() throws Exception {
		HttpServletRequest request = 
				(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		ProjectDetailPageViewEntity pageViewEntity = new ProjectDetailPageViewEntity();
		
		UserEntity user = getUserDetailsService().loadFullLoggedUser();
		pageViewEntity.setUser(user);
		pageViewEntity.setProject(getProject());
		pageViewEntity.setViewedAt(new Timestamp(System.currentTimeMillis()));
		pageViewEntity.setIpAddress(request.getRemoteAddr());
		
		getPageViewService().save(pageViewEntity);
		
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			findAlsoViewedProjectList();
			findRelatedProjectList();
			
			if (getProject().getDescription() != null) {
				this.projectDescritionParagraphs = getProject().getDescription().split("\n");
			}
			
			if (getProject().getOhLohAnalysis() != null) {
				this.factoidList = getProject().getOhLohAnalysis().getOhLohFactoids();
				this.analysisLanguages = getProject().getOhLohAnalysis().getOhLohAnalysisLanguages();
			}
			
			this.enlistmentCount = getEnlistmentService().countAllByProject(getProject());
			if (enlistmentCount != null && enlistmentCount > 0) {
				this.enlistmentList = getEnlistmentService().findByProject(getProject());
			}
			
			this.linkCount = getLinkService().countAllByProject(getProject());
			if (linkCount != null && linkCount > 0) {
				this.linkList = getLinkService().findByProject(getProject());
			}
			
			this.overallPreferenceCount = getOverallPreferenceService().countAllLastByProject(getProject().getId());
			
			//calculando valores médios de preferência
			UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
			
			this.averagePreference = getOverallPreferenceService().averagePreferenceByProject(getProject().getId());
			if (currentUser != null) {
				this.userPreference = getOverallPreferenceService().findLastByProjectAndUser(getProject().getId(), currentUser.getId());
			}
			
			try {
				savePageViewInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Utiliza busca por conteúdo pra recomendar projetos similares.
	 */
	protected void findRelatedProjectList() {
		try {
			this.relatedProjectEntities = getSearchService().findRelatedProjects(getProject(), 6);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Recomenda outros projetos vistos pelos mesmos usuários que viram o projeto corrente
	 */
	protected void findAlsoViewedProjectList() {
		try {
			//limpa lista atual de projetos recomendados
			this.alsoViewedProjectList = new ArrayList<>();

			GenericBooleanPrefItemBasedRecommender recommender = getPageViewService().buildProjectRecommender();
			List<RecommendedItem> recommendedItemList = recommender.mostSimilarItems(getProject().getId(), 6);
			
			if (recommendedItemList != null) {
				for (RecommendedItem recommendedItem : recommendedItemList) {
					OhLohProjectEntity recommendedProject = getProjectService().findById(recommendedItem.getItemID());
					this.alsoViewedProjectList.add(recommendedProject);
				}
			}
		} catch (TasteException e1) {
			e1.printStackTrace();
		}
	}
	
	public String detailProject() {
		return "projectDetail";
	}

	public OhLohProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(OhLohProjectService projectService) {
		this.projectService = projectService;
	}

	public OhLohEnlistmentService getEnlistmentService() {
		return enlistmentService;
	}

	public void setEnlistmentService(OhLohEnlistmentService enlistmentService) {
		this.enlistmentService = enlistmentService;
	}

	public OhLohAnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(OhLohAnalysisService analysisService) {
		this.analysisService = analysisService;
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
	
	public Long getLinkCount() {
		return linkCount;
	}
	
	public OhLohLinkService getLinkService() {
		return linkService;
	}
	
	public void setLinkService(OhLohLinkService linkService) {
		this.linkService = linkService;
	}

	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public ProjectDetailPageViewService getPageViewService() {
		return pageViewService;
	}
	
	public void setPageViewService(ProjectDetailPageViewService pageViewService) {
		this.pageViewService = pageViewService;
	}
	
	public SearchService getSearchService() {
		return searchService;
	}
	
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

	public List<OhLohEnlistmentEntity> getEnlistmentList() {
		return enlistmentList;
	}
	
	public List<OhLohLinkEntity> getLinkList() {
		return linkList;
	}
	
	public Long getEnlistmentCount() {
		return enlistmentCount;
	}
	
	public Long getOverallPreferenceCount() {
		return overallPreferenceCount;
	}

	public String[] getProjectDescritionParagraphs() {
		return projectDescritionParagraphs;
	}
	
	public List<OhLohFactoidEntity> getFactoidList() {
		return factoidList;
	}
	
	public OhLohAnalysisLanguagesEntity getAnalysisLanguages() {
		return analysisLanguages;
	}
	
	public PreferenceEntity getAveragePreference() {
		return averagePreference;
	}
	
	public PreferenceEntity getUserPreference() {
		return userPreference;
	}
	
	public List<OhLohProjectEntity> getAlsoViewedProjectList() {
		return alsoViewedProjectList;
	}
	
	public List<OhLohProjectEntity> getRelatedProjectEntities() {
		return relatedProjectEntities;
	}
}
