package br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(
			name=UserEntity.NODE_NAME,
			uniqueConstraints=@UniqueConstraint(columnNames={"token"})
)
public class PasswordChangeRequestEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2666917398014687968L;
	public final static String NODE_NAME = "password_change_request";
	
	@Id
	@SequenceGenerator(name="password_change_request_id", sequenceName="password_change_request_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="password_change_request_id")
	private Long id;
	
	private String token;
	
	@Column(name = "expiration_date")
	private Timestamp expirationDate;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private UserEntity user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	
}
