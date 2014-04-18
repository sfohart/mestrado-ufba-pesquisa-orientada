package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.factoid.OhLohFactoidEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.EnlistmentService;
import br.ufba.dcc.mestrado.computacao.service.base.LinkService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;



@ManagedBean(name="projectDetailMB", eager=true)
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="projectDetailMapping",
			beanName="projectDetailMB", 
			pattern="/detail/#{ /[0-9]+/ projectId}/",
			viewId="/detail/projectDetail.jsf")	
})
public class ProjectDetailManageBean extends ProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3959153545005214806L;

	
	@ManagedProperty("#{enlistmentService}")
	private EnlistmentService enlistmentService;
	
	@ManagedProperty("#{overallRatingService}")
	private OverallRatingService overallRatingService;
	
	@ManagedProperty("#{linkService}")
	private LinkService linkService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{projectDetailPageViewService}")
	private ProjectDetailPageViewService pageViewService;
	
	
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
		super();
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
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
			if (getProject().getDescription() != null) {
				this.projectDescritionParagraphs = getProject().getDescription().split("\n");
			}
			
			if (getProject().getAnalysis() != null) {
				this.factoidList = getProject().getAnalysis().getFactoids();
				this.analysisLanguages = getProject().getAnalysis().getAnalysisLanguages();
			}
			
			this.enlistmentCount = getEnlistmentService().countAllByProject(getProject());
			if (enlistmentCount != null && enlistmentCount > 0) {
				this.enlistmentList = getEnlistmentService().findByProject(getProject());
			}
			
			this.linkCount = getLinkService().countAllByProject(getProject());
			if (linkCount != null && linkCount > 0) {
				this.linkList = getLinkService().findByProject(getProject());
			}
			
			this.overallPreferenceCount = getOverallRatingService().countAllLastByProject(getProject().getId());
			
			//calculando valores médios de preferência
			UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
			
			this.averagePreference = getOverallRatingService().averagePreferenceByItem(getProject().getId());
			if (currentUser != null) {
				this.userPreference = getOverallRatingService().findLastOverallPreferenceByUserAndItem(getProject().getId(), currentUser.getId());
			}
			
			try {
				savePageViewInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public String detailProject() {
		return "projectDetail";
	}


	public EnlistmentService getEnlistmentService() {
		return enlistmentService;
	}

	public void setEnlistmentService(EnlistmentService enlistmentService) {
		this.enlistmentService = enlistmentService;
	}
	
	public OverallRatingService getOverallRatingService() {
		return overallRatingService;
	}

	public void setOverallRatingService(OverallRatingService overallRatingService) {
		this.overallRatingService = overallRatingService;
	}
	
	public Long getLinkCount() {
		return linkCount;
	}
	
	public LinkService getLinkService() {
		return linkService;
	}
	
	public void setLinkService(LinkService linkService) {
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
	
	
}
