package br.ufba.dcc.mestrado.computacao.entities.ohloh.stack;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;

@Entity
@Table(name = OhLohStackEntity.NODE_NAME)
public class OhLohStackEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1367617631361506867L;

	public final static String NODE_NAME = "stack";

	@Id
	
	private Long id;
	
	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "project_count")
	private Long projectCount;

	@OneToMany(mappedBy = "stack", cascade=CascadeType.ALL)
	private List<OhLohStackEntryEntity> stackEntries;

	@Column(name = "account_id", insertable = false, updatable = false)
	private Long acountId;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private OhLohAccountEntity account;

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

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getProjectCount() {
		return projectCount;
	}

	public void setProjectCount(Long projectCount) {
		this.projectCount = projectCount;
	}

	public List<OhLohStackEntryEntity> getStackEntries() {
		return stackEntries;
	}
	
	public void setStackEntries(List<OhLohStackEntryEntity> stackEntries) {
		this.stackEntries = stackEntries;
	}

	public Long getAcountId() {
		return acountId;
	}

	public void setAcountId(Long acountId) {
		this.acountId = acountId;
	}

	public OhLohAccountEntity getAccount() {
		return account;
	}

	public void setAccount(OhLohAccountEntity account) {
		this.account = account;
	}

}
