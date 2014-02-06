package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.basic.ProjectDetailPageViewService;

@ManagedBean(name="alsoViewedProjectMB")
@ViewScoped
public class AlsoViewedProjectManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -722099582447182349L;
	
	private static final Integer SAMPLE_MAX_RESULTS = 6;
	private static final Integer TOP10_MAX_RESULTS = 10;
	
	private OhLohProjectEntity project;
	
	private Integer maxResults;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService projectService;
	
	@ManagedProperty("#{projectDetailPageViewService}")
	private ProjectDetailPageViewService pageViewService;
	
	private List<OhLohProjectEntity> alsoViewedProjectList;
	
	public AlsoViewedProjectManagedBean() {
		this.project = new OhLohProjectEntity();
		this.maxResults = SAMPLE_MAX_RESULTS;
	}

	public void configureTopTenResults(ComponentSystemEvent event) {
		this.maxResults = TOP10_MAX_RESULTS;
	}
	
	public void init(ComponentSystemEvent event) {
		if (getProject() != null && getProject().getId() != null) {
			this.project = getProjectService().findById(getProject().getId());
			
			findAlsoViewedProjectList();
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
			List<RecommendedItem> recommendedItemList = recommender.mostSimilarItems(getProject().getId(), getMaxResults());
			
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

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public ProjectDetailPageViewService getPageViewService() {
		return pageViewService;
	}

	public void setPageViewService(ProjectDetailPageViewService pageViewService) {
		this.pageViewService = pageViewService;
	}

	public OhLohProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(OhLohProjectService projectService) {
		this.projectService = projectService;
	}

	public List<OhLohProjectEntity> getAlsoViewedProjectList() {
		return alsoViewedProjectList;
	}

	public void setAlsoViewedProjectList(
			List<OhLohProjectEntity> alsoViewedProjectList) {
		this.alsoViewedProjectList = alsoViewedProjectList;
	}

}
