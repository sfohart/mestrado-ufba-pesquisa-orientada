package br.ufba.dcc.mestrado.computacao.entities.recommender.preference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;

@Entity
@Table(name = PreferenceEntryEntity.NODE_NAME)
public class PreferenceEntryEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2788872292886109032L;

	public final static String NODE_NAME = "recommender_preference_entry";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "criterium_id", insertable = false, updatable = false)
	private Long criteriumId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "criterium_id", referencedColumnName = "id")
	private RecommenderCriteriumEntity criterium;
	
	@ManyToOne
	@JoinColumn(name = "preference_id", referencedColumnName = "id")
	private PreferenceEntity preference;
	
	@Column(name = "value_preference")
	private Double value;
	
	@Column(name = "not_available")
	private Boolean notAvailable;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCriteriumId() {
		return criteriumId;
	}

	public void setCriteriumId(Long criteriumId) {
		this.criteriumId = criteriumId;
	}

	public RecommenderCriteriumEntity getCriterium() {
		return criterium;
	}

	public void setCriterium(RecommenderCriteriumEntity criterium) {
		this.criterium = criterium;
	}

	public PreferenceEntity getPreference() {
		return preference;
	}

	public void setPreference(PreferenceEntity preference) {
		this.preference = preference;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Boolean getNotAvailable() {
		return notAvailable;
	}

	public void setNotAvailable(Boolean notAvailable) {
		this.notAvailable = notAvailable;
	}
	
}
