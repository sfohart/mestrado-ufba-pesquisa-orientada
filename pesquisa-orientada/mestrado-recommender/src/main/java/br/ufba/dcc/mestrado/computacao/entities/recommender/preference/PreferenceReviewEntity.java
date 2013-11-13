package br.ufba.dcc.mestrado.computacao.entities.recommender.preference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name = PreferenceReviewEntity.NODE_NAME)
public class PreferenceReviewEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8875666177399783171L;

	public final static String NODE_NAME = "recommender_preference_review";
	
	@Id
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "preference_id", referencedColumnName = "id")
	private PreferenceEntity preference;

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

	
}
