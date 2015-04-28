package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommendationEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.service.base.RecommendationEvaluationService;

public abstract class RecommendedProjectManagedBean extends ProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4427973769068757558L;
	
	@ManagedProperty("#{recommendationEvaluationService}")
	private RecommendationEvaluationService recommendationEvaluationService;
	
	public RecommendedProjectManagedBean() {
		super();
	}
	
	public RecommendationEvaluationService getRecommendationEvaluationService() {
		return recommendationEvaluationService;
	}

	public void setRecommendationEvaluationService(
			RecommendationEvaluationService recommendationEvaluationService) {
		this.recommendationEvaluationService = recommendationEvaluationService;
	}

	protected RecommendationEvaluationEntity buildRecommendationEvaluation(Long selectedProjectId) {
		return null;
	}
	
	public String saveRecommenderEvaluation() {
			
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String selectedProjectIdParam = facesContext.getExternalContext().getRequestParameterMap().get(SELECTED_PROJECT_PARAM);
		
		if (! StringUtils.isEmpty(selectedProjectIdParam)) {		
			Long selectedProjectId = Long.valueOf(selectedProjectIdParam);
			
			RecommendationEvaluationEntity recommendationEvaluationEntity = buildRecommendationEvaluation(selectedProjectId);
			
			if (recommendationEvaluationEntity != null) {
				try {
					recommendationEvaluationService.save(recommendationEvaluationEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return String.format("/detail/projectDetail.jsf?projectId=%d&faces-redirect=true", selectedProjectId);
		} else {
			return null;
		}
		
	}

}
