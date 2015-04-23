package br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;

@Entity

@Table(name = RecommendationEvaluationEntity.NODE_NAME)
public class RecommendationEvaluationEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7027169002518715822L;
	
	public final static String NODE_NAME = "recommendation_evaluation";
	
	@Id
	@SequenceGenerator(name="recommendation_evaluation_id", sequenceName="recommendation_evaluation_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="recommendation_evaluation_id")
	private Long id;
	
	@Column(name = "recommendation_id", insertable = false, updatable = false)
	private Long userRecommendationId;
	
	@ManyToOne
	@JoinColumn(name = "recommendation_id", referencedColumnName = "id")
	private UserRecommendationEntity userRecommendation;
	
	@ManyToOne
	@JoinColumn(name = "selected_project_id", referencedColumnName = "id")
	private OpenHubProjectEntity selectedProject;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserRecommendationId() {
		return userRecommendationId;
	}

	public void setUserRecommendationId(Long userRecommendationId) {
		this.userRecommendationId = userRecommendationId;
	}

	public UserRecommendationEntity getUserRecommendation() {
		return userRecommendation;
	}

	public void setUserRecommendation(UserRecommendationEntity userRecommendation) {
		this.userRecommendation = userRecommendation;
	}

	public OpenHubProjectEntity getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(OpenHubProjectEntity selectedProject) {
		this.selectedProject = selectedProject;
	}

	
	
	
}
