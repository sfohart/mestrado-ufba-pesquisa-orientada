package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommendationEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommenderEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.RecommenderEvaluationService;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.BaseColaborativeFilteringService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "relatedProjectMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="relatedProjectMapping",
				beanName="relatedProjectMB", 
				pattern="/detail/#{ /[0-9]+/ projectId}/relatedProject",
				viewId="/detail/relatedProjectList.jsf")	
})
public class RelatedProjectManagedBean extends ProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -722099582447182349L;
	
	private static final Integer SAMPLE_MAX_RESULTS = 6;
	private static final Integer TOP10_MAX_RESULTS = 10;

	private static final String SELECTED_PROJECT_PARAM = "selectedProjectId";


	@ManagedProperty("#{searchService}")
	private SearchService searchService;
	
	@ManagedProperty("#{mahoutColaborativeFilteringService}")
	private BaseColaborativeFilteringService colaborativeFilteringService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{recommenderEvaluationService}")
	private RecommenderEvaluationService recommenderEvaluationService;

	private RecommenderEvaluationEntity alsoViewedProjecstRecommendation;	
	private RecommenderEvaluationEntity similarProjectsRecomendation;
	
	

	private Integer maxResults;

	public RelatedProjectManagedBean() {
		super();
		this.maxResults = SAMPLE_MAX_RESULTS;
	}
	
	public void configureTopTenResults(ComponentSystemEvent event) {
		this.maxResults = TOP10_MAX_RESULTS;
	}
	
	public void enableSimilarProjectsRecommendation(ComponentSystemEvent event) {
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
			configureSimilarProjectsRecommendation();
		}
	}

	public void enableAlsoViewedProjectsRecommendation(ComponentSystemEvent event) {
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
			configureAlsoViewedProjectsRecommendation();
		}
	}
	
	public String saveRecommenderEvaluation() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String selectedProjectIdParam = facesContext.getExternalContext().getRequestParameterMap().get(SELECTED_PROJECT_PARAM);
		Long selectedProjectId = Long.valueOf(selectedProjectIdParam);
		
		RecommenderEvaluationEntity recommenderEvaluationEntity = null;
		
		if (similarProjectsRecomendation != null && similarProjectsRecomendation.getRecommendedProjects() != null) {
			for (OpenHubProjectEntity project : similarProjectsRecomendation.getRecommendedProjects()) {
				if (project.getId() == selectedProjectId) {
					recommenderEvaluationEntity = similarProjectsRecomendation;
					recommenderEvaluationEntity.setSelectedProject(project);
					break;
				}
			}
		}
		
		if (recommenderEvaluationEntity == null
				&& alsoViewedProjecstRecommendation != null 
				&& alsoViewedProjecstRecommendation.getRecommendedProjects() != null) {
			
			for (OpenHubProjectEntity project : alsoViewedProjecstRecommendation.getRecommendedProjects()) {
				if (project.getId() == selectedProjectId) {
					recommenderEvaluationEntity = alsoViewedProjecstRecommendation;
					recommenderEvaluationEntity.setSelectedProject(project);
					break;
				}
			}			
		}
		
		if (recommenderEvaluationEntity != null) {
			try {
				getRecommenderEvaluationService().save(recommenderEvaluationEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.format("/detail/projectDetail.jsf?projectId=%d&faces-redirect=true", selectedProjectId);
		
	}
	

	protected void configureAlsoViewedProjectsRecommendation() {
		alsoViewedProjecstRecommendation = new RecommenderEvaluationEntity();	
		
		List<OpenHubProjectEntity> recommendationList = getColaborativeFilteringService().recommendViewedProjectsByItem(getProject().getId(), getMaxResults());
		UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		Timestamp recommendationDate = new Timestamp(System.currentTimeMillis());
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		
		alsoViewedProjecstRecommendation.setRecommendationType(RecommendationEnum.COLABORATIVE_FILTERING_RECOMMENDATION);
		alsoViewedProjecstRecommendation.setRecommendedProjects(recommendationList);
		alsoViewedProjecstRecommendation.setUser(currentUser);
		alsoViewedProjecstRecommendation.setRecommendationDate(recommendationDate);	
		alsoViewedProjecstRecommendation.setViewId(viewId);
		
		try {
			alsoViewedProjecstRecommendation = getRecommenderEvaluationService().save(alsoViewedProjecstRecommendation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Utiliza busca por conteúdo pra recomendar projetos similares.
	 */
	protected void configureSimilarProjectsRecommendation() {
		try {
			
			similarProjectsRecomendation = new RecommenderEvaluationEntity();
			
			List <OpenHubProjectEntity> recommendationList = getSearchService().findRelatedProjects(getProject(), 0, getMaxResults());
			UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
			Timestamp recommendationDate = new Timestamp(System.currentTimeMillis());
			String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			
			similarProjectsRecomendation.setRecommendationType(RecommendationEnum.CONTENT_BASED_RECOMMENDATION);
			similarProjectsRecomendation.setRecommendedProjects(recommendationList);
			similarProjectsRecomendation.setUser(currentUser);
			similarProjectsRecomendation.setRecommendationDate(recommendationDate);
			similarProjectsRecomendation.setViewId(viewId);
						
			try {
				similarProjectsRecomendation = getRecommenderEvaluationService().save(similarProjectsRecomendation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public BaseColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public void setColaborativeFilteringService(
			BaseColaborativeFilteringService colaborativeFilteringService) {
		this.colaborativeFilteringService = colaborativeFilteringService;
	}
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public RecommenderEvaluationService getRecommenderEvaluationService() {
		return recommenderEvaluationService;
	}

	public void setRecommenderEvaluationService(
			RecommenderEvaluationService recommenderEvaluationService) {
		this.recommenderEvaluationService = recommenderEvaluationService;
	}

	public RecommenderEvaluationEntity getAlsoViewedProjecstRecommendation() {
		return alsoViewedProjecstRecommendation;
	}

	public void setAlsoViewedProjecstRecommendation(
			RecommenderEvaluationEntity alsoViewedProjecstRecommendation) {
		this.alsoViewedProjecstRecommendation = alsoViewedProjecstRecommendation;
	}

	public RecommenderEvaluationEntity getSimilarProjectsRecomendation() {
		return similarProjectsRecomendation;
	}

	public void setSimilarProjectsRecomendation(
			RecommenderEvaluationEntity similarProjectsRecomendation) {
		this.similarProjectsRecomendation = similarProjectsRecomendation;
	}

	
}
