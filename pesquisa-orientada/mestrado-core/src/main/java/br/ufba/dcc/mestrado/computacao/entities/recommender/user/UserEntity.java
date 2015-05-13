package br.ufba.dcc.mestrado.computacao.entities.recommender.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.codec.binary.Hex;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.account.OpenHubAccountEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;

@Entity
@Table(name = UserEntity.NODE_NAME)
public class UserEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2358398966883883246L;

	public final static String NODE_NAME = "web_user";
	
	@Id
	@SequenceGenerator(name="web_user_id", sequenceName="web_user_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="web_user_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ohloh_account_id", referencedColumnName="id")
	private OpenHubAccountEntity ohLohAccount;
		
	@Column(unique = true)
	private String login;
	
	private String password;
	
	@Column(unique = true)
	private String email;
		
	private String name;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "facebook_account")
	private String facebookAccount;

	@Column(name = "twitter_account")
	private String twitterAccount;
	
	@Column(name = "google_plus_account")
	private String googlePlusAccount;
	
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
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
			name="user_interesttag",
			joinColumns=@JoinColumn(name = "user_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name = "tag_id", referencedColumnName="id"),
			uniqueConstraints=@UniqueConstraint(columnNames={"user_id","tag_id"})
			)
	private List<OpenHubTagEntity> interestTags;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public OpenHubAccountEntity getOhLohAccount() {
		return ohLohAccount;
	}

	public void setOhLohAccount(OpenHubAccountEntity ohLohAccount) {
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

	public String getGooglePlusAccount() {
		return googlePlusAccount;
	}

	public void setGooglePlusAccount(String googlePlusAccount) {
		this.googlePlusAccount = googlePlusAccount;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getEncodedEmail() {
		String encoded = "";
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			byte[] bytes = getEmail().getBytes("CP1252");
			encoded = Hex.encodeHexString(messageDigest.digest(bytes));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return encoded;
	}
	
	public List<OpenHubTagEntity> getInterestTags() {
		return this.interestTags;
	}
	
	public void setInterestTags(List<OpenHubTagEntity> interestTags) {
		this.interestTags = interestTags;
	}
	
}
