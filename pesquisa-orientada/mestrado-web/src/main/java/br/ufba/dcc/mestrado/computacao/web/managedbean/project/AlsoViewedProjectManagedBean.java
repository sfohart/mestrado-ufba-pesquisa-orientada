package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;

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
	
	@ManagedProperty("#{mahoutColaborativeFilteringService}")
	private BaseColaborativeFilteringService colaborativeFilteringService;
	
	private List<OpenHubProjectEntity> alsoViewedProjectList;
	
	public AlsoViewedProjectManagedBean() {
		super();
		this.maxResults = SAMPLE_MAX_RESULTS;
	}

	public BaseColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public void setColaborativeFilteringService(
			BaseColaborativeFilteringService colaborativeFilteringService) {
		this.colaborativeFilteringService = colaborativeFilteringService;
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
		//limpa lista atual de projetos recomendados
		this.alsoViewedProjectList = new ArrayList<>();
		
		this.alsoViewedProjectList = getColaborativeFilteringService().recommendViewedProjectsByItem(getProject().getId(), getMaxResults());
	}


	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	
	public List<OpenHubProjectEntity> getAlsoViewedProjectList() {
		return alsoViewedProjectList;
	}

	public void setAlsoViewedProjectList(
			List<OpenHubProjectEntity> alsoViewedProjectList) {
		this.alsoViewedProjectList = alsoViewedProjectList;
	}

}
