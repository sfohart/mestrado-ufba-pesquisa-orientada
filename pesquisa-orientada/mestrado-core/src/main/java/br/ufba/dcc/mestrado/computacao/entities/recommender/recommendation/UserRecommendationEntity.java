package br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation;

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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

@Entity
@Table(name = UserRecommendationEntity.NODE_NAME)
public class UserRecommendationEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4157671231273596443L;

	public final static String NODE_NAME = "user_recommendation";
	
	@Id
	@SequenceGenerator(name="user_recommendation_id", sequenceName="user_recommendation_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_recommendation_id")
	private Long id;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="user_recommendation_projects",
		joinColumns=@JoinColumn(name="user_recommendation_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="project_id", referencedColumnName="id"),
		uniqueConstraints=@UniqueConstraint(columnNames={"user_recommendation_id","project_id"})
	)
	private List<OpenHubProjectEntity> recommendedProjects;
	
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;
	
	@Column(name = "view_uri")
	private String viewUri;
	
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

	public List<OpenHubProjectEntity> getRecommendedProjects() {
		return recommendedProjects;
	}

	public void setRecommendedProjects(
			List<OpenHubProjectEntity> recommendedProjects) {
		this.recommendedProjects = recommendedProjects;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getViewUri() {
		return viewUri;
	}

	public void setViewUri(String viewUri) {
		this.viewUri = viewUri;
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
	
	
	
}
