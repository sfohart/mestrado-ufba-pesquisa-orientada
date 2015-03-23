package br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.criterium;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.UserEntity;

@Entity
@Table(name = UserRecommenderCriteriumEntity.NODE_NAME)
public class UserRecommenderCriteriumEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3629303415854305506L;
	
	public final static String NODE_NAME = "user_recommender_criterium";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;
	
	@Column(name = "criterium_id", insertable = false, updatable = false)
	private Long criteriumId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "criterium_id", referencedColumnName = "id")
	private RecommenderCriteriumEntity criterium;
	
	@Column (name = "weight")
	private Float weight;

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

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}
	
	

}
