package br.ufba.dcc.mestrado.computacao.entities.recommender.criterium;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;

@Entity
@Table(name = UserRecommenderCriteriumEntity.NODE_NAME)
public class UserRecommenderCriteriumEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3629303415854305506L;
	
	public final static String NODE_NAME = "user_recommender_criterium";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "account_id", insertable = false, updatable = false)
	private Long accountId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private OhLohAccountEntity account;
	
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public OhLohAccountEntity getAccount() {
		return account;
	}

	public void setAccount(OhLohAccountEntity account) {
		this.account = account;
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
