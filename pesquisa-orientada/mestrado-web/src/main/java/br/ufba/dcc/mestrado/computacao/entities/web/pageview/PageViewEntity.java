package br.ufba.dcc.mestrado.computacao.entities.web.pageview;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

@MappedSuperclass
public abstract class PageViewEntity implements BaseEntity<Long> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4071916917730573606L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;	
	
	@Column(name = "viewed_at")
	private Timestamp viewedAt;
	
	@Column(name = "ip_address")
	private String ipAddress;

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

	public Timestamp getViewedAt() {
		return viewedAt;
	}

	public void setViewedAt(Timestamp viewedAt) {
		this.viewedAt = viewedAt;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
	
}