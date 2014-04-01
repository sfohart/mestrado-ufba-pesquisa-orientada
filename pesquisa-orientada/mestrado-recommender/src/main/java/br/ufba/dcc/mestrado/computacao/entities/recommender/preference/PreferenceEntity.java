package br.ufba.dcc.mestrado.computacao.entities.recommender.preference;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

@Entity
@Table(name = PreferenceEntity.NODE_NAME)
public class PreferenceEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5571145769221686191L;

	public final static String NODE_NAME = "recommender_preference";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;

	@Column(name = "project_id", updatable = false, insertable = false)
	private Long projectId;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private OhLohProjectEntity project;
	
	@Column(name = "value_preference")
	private Double value;
	
	@OneToMany(mappedBy = "preference", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<PreferenceEntryEntity> preferenceEntryList;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "review_id", referencedColumnName = "id")
	private PreferenceReviewEntity preferenceReview;
	
	@Column(name = "registered_at")
	private Timestamp registeredAt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public List<PreferenceEntryEntity> getPreferenceEntryList() {
		return preferenceEntryList;
	}

	public void setPreferenceEntryList(List<PreferenceEntryEntity> preferenceEntryList) {
		this.preferenceEntryList = preferenceEntryList;
	}

	public PreferenceReviewEntity getPreferenceReview() {
		return preferenceReview;
	}

	public void setPreferenceReview(PreferenceReviewEntity preferenceReview) {
		this.preferenceReview = preferenceReview;
	}

	public Timestamp getRegisteredAt() {
		return registeredAt;
	}
	
	public void setRegisteredAt(Timestamp registeredAt) {
		this.registeredAt = registeredAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreferenceEntity other = (PreferenceEntity) obj;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
