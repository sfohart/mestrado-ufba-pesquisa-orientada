package br.ufba.dcc.mestrado.computacao.entities.ohloh.core.kudoskore;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name=OhLohKudoScoreEntity.NODE_NAME)
public class OhLohKudoScoreEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4697647432356330723L;

	public final static String NODE_NAME = "kudo_score";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_at")
	private Timestamp createdAt;
	
	@Column(name="kudo_rank")
	private Long kudoRank;
	
	@Column(name="position")
	private Long position;
	
	@Column(name="max_position")
	private Long maxPosition;
	
	@Column(name="position_delta")
	private Long positionDelta;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Long getKudoRank() {
		return kudoRank;
	}

	public void setKudoRank(Long kudoRank) {
		this.kudoRank = kudoRank;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public Long getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(Long maxPosition) {
		this.maxPosition = maxPosition;
	}

	public Long getPositionDelta() {
		return positionDelta;
	}

	public void setPositionDelta(Long positionDelta) {
		this.positionDelta = positionDelta;
	}


}
