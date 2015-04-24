package br.ufba.dcc.mestrado.computacao.entities.recommender.preference;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

@Entity
@Table(name = PreferenceReviewEntity.NODE_NAME)
public class PreferenceReviewEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8875666177399783171L;

	public final static String NODE_NAME = "recommender_preference_review";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "preference_id", referencedColumnName = "id")
	private PreferenceEntity preference;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="review_useful_users",
		joinColumns=@JoinColumn(name="review_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
		uniqueConstraints=@UniqueConstraint(columnNames={"review_id","user_id"})
	)
	private Set<UserEntity> usefulList;
	
	@Column(name = "useful_count")
	private Long usefulCount = 0L;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="review_useless_users",
		joinColumns=@JoinColumn(name="review_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
		uniqueConstraints=@UniqueConstraint(columnNames={"review_id","user_id"})
	)
	private Set<UserEntity> uselessList;
	
	@Column(name = "useless_count")
	private Long uselessCount = 0L;
	
	@Column(name = "review_ranking")
	private Double reviewRanking = 0D;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PreferenceEntity getPreference() {
		return preference;
	}

	public void setPreference(PreferenceEntity preference) {
		this.preference = preference;
	}

	public Set<UserEntity> getUsefulList() {
		return usefulList;
	}

	public void setUsefulList(Set<UserEntity> usefulList) {
		this.usefulList = usefulList;
	}

	public Set<UserEntity> getUselessList() {
		return uselessList;
	}

	public void setUselessList(Set<UserEntity> uselessList) {
		this.uselessList = uselessList;
	}

	public Long getUsefulCount() {
		return usefulCount;
	}

	public void setUsefulCount(Long usefulCount) {
		this.usefulCount = usefulCount;
	}

	public Long getUselessCount() {
		return uselessCount;
	}

	public void setUselessCount(Long uselessCount) {
		this.uselessCount = uselessCount;
	}

	public Double getReviewRanking() {
		return reviewRanking;
	}

	public void setReviewRanking(Double reviewRanking) {
		this.reviewRanking = reviewRanking;
	}

}
