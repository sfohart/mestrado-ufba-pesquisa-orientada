package br.ufba.dcc.mestrado.computacao.entities.recommender.user;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;

@Entity
@Table(name=UserEntity.NODE_NAME)
public class UserEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2358398966883883246L;

	public final static String NODE_NAME = "web_user";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ohloh_account_id", referencedColumnName="id")
	private OhLohAccountEntity ohLohAccount;
		
	@Column(nullable = false, unique = true)
	private String login;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
		
	private String name;
	
	@Column(name = "facebook_account")
	private String facebookAccount;

	@Column(name = "twitter_account")
	private String twitterAccount;
	
	@ElementCollection(targetClass=RoleEnum.class)
	@Enumerated(EnumType.ORDINAL)
	@CollectionTable(
			name="user_role",
			joinColumns=@JoinColumn(name="user_id")
		)
	@Column(name="role_id")
	private List<RoleEnum> roleList;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@Column(name = "locked")
	private Boolean locked;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public OhLohAccountEntity getOhLohAccount() {
		return ohLohAccount;
	}

	public void setOhLohAccount(OhLohAccountEntity ohLohAccount) {
		this.ohLohAccount = ohLohAccount;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacebookAccount() {
		return facebookAccount;
	}

	public void setFacebookAccount(String facebookAccount) {
		this.facebookAccount = facebookAccount;
	}

	public String getTwitterAccount() {
		return twitterAccount;
	}

	public void setTwitterAccount(String twitterAccount) {
		this.twitterAccount = twitterAccount;
	}

	public List<RoleEnum> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleEnum> roleList) {
		this.roleList = roleList;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		UserEntity other = (UserEntity) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
	
	
	
}
