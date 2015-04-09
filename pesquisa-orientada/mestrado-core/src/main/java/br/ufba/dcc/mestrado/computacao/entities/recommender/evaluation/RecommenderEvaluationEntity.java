package br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

@Entity
@Table(name = RecommenderEvaluationEntity.NODE_NAME)
public class RecommenderEvaluationEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7027169002518715822L;
	
	public final static String NODE_NAME = "recommender_evaluation";
	
	@Id
	@SequenceGenerator(name="recommender_evaluation_id", sequenceName="recommender_evaluation_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="recommender_evaluation_id")
	private Long id;
	
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name = "selected_project_id", referencedColumnName = "id")
	private OpenHubProjectEntity selectedProject;
	
	@Column(name = "view_id")
	private String viewId;
		
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="recommender_evaluation_projects",
		joinColumns=@JoinColumn(name="recommender_evaluation_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="project_id", referencedColumnName="id"),
		uniqueConstraints=@UniqueConstraint(columnNames={"recommender_evaluation_id","project_id"})
	)
	private List<OpenHubProjectEntity> recommendedProjects;
	
	@Column(name = "recommendation_date")
	private Timestamp recommendationDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "recommendation_type")
	private RecommendationEnum recommendationType;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public OpenHubProjectEntity getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(OpenHubProjectEntity selectedProject) {
		this.selectedProject = selectedProject;
	}

	public List<OpenHubProjectEntity> getRecommendedProjects() {
		return recommendedProjects;
	}

	public void setRecommendedProjects(
			List<OpenHubProjectEntity> recommendedProjects) {
		this.recommendedProjects = recommendedProjects;
	}

	public Timestamp getRecommendationDate() {
		return recommendationDate;
	}

	public void setRecommendationDate(Timestamp recommendationDate) {
		this.recommendationDate = recommendationDate;
	}

	public RecommendationEnum getRecommendationType() {
		return recommendationType;
	}

	public void setRecommendationType(RecommendationEnum recommendationType) {
		this.recommendationType = recommendationType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	
	
	
}
