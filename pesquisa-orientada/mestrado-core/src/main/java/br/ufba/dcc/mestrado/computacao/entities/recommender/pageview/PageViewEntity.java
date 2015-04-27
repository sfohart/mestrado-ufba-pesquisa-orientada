package br.ufba.dcc.mestrado.computacao.entities.recommender.pageview;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

@MappedSuperclass
public abstract class PageViewEntity implements BaseEntity<Long> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4071916917730573606L;
	
	@Id
	@SequenceGenerator(name="page_view_id", sequenceName="page_view_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="page_view_id")
	private Long id;
	
	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;	
	
	@Column(name = "viewed_at")
	private Timestamp viewedAt;
	
	@Column(name = "user_hash")
	private Integer userHash;

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

	public Integer getUserHash() {
		return userHash;
	}

	public void setUseHash(Integer userHash) {
		this.userHash = userHash;
	}

	
}
