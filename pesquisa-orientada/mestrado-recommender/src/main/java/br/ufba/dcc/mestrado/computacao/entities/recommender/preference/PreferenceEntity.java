package br.ufba.dcc.mestrado.computacao.entities.recommender.preference;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;

@Entity
@Table(name = PreferenceEntity.NODE_NAME)
public class PreferenceEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5571145769221686191L;

	public final static String NODE_NAME = "recommender_preference";

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "account_id", insertable = false, updatable = false)
	private Long accountId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private OhLohAccountEntity account;

	@Column(name = "project_id", updatable = false, insertable = false)
	private Long projectId;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private OhLohProjectEntity project;
	
	@OneToMany(mappedBy = "preference")
	private List<PreferenceEntryEntity> preferenceEntryList;
	
	@ManyToOne
	private PreferenceReviewEntity preferenceReview;
	
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

	
}
