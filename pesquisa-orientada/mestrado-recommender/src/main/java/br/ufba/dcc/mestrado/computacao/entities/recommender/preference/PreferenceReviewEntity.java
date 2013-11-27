package br.ufba.dcc.mestrado.computacao.entities.recommender.preference;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	@GeneratedValue
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "preference_id", referencedColumnName = "id")
	private PreferenceEntity preference;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
		name="review_useful_users",
		joinColumns=@JoinColumn(name="review_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id")
	)
	private List<UserEntity> usefulList;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
		name="review_useless_users",
		joinColumns=@JoinColumn(name="review_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id")
	)
	private List<UserEntity> uselessList;

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

	public List<UserEntity> getUsefulList() {
		return usefulList;
	}

	public void setUsefulList(List<UserEntity> usefulList) {
		this.usefulList = usefulList;
	}

	public List<UserEntity> getUselessList() {
		return uselessList;
	}

	public void setUselessList(List<UserEntity> uselessList) {
		this.uselessList = uselessList;
	}

	
}
