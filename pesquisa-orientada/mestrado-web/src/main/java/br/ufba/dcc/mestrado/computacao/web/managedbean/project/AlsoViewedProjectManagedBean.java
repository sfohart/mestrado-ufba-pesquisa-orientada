package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

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
import br.ufba.dcc.mestrado.computacao.service.basic.ProjectDetailPageViewService;

@ManagedBean(name="alsoViewedProjectMB")
@ViewScoped
public class AlsoViewedProjectManagedBean extends ProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -722099582447182349L;
	
	private static final Integer SAMPLE_MAX_RESULTS = 6;
	private static final Integer TOP10_MAX_RESULTS = 10;
	
	
	private Integer maxResults;
	
	
	@ManagedProperty("#{projectDetailPageViewService}")
	private ProjectDetailPageViewService pageViewService;
	
	private List<OhLohProjectEntity> alsoViewedProjectList;
	
	public AlsoViewedProjectManagedBean() {
		super();
		this.maxResults = SAMPLE_MAX_RESULTS;
	}

	public void configureTopTenResults(ComponentSystemEvent event) {
		this.maxResults = TOP10_MAX_RESULTS;
	}
	
	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
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

	public List<OhLohProjectEntity> getAlsoViewedProjectList() {
		return alsoViewedProjectList;
	}

	public void setAlsoViewedProjectList(
			List<OhLohProjectEntity> alsoViewedProjectList) {
		this.alsoViewedProjectList = alsoViewedProjectList;
	}

}
