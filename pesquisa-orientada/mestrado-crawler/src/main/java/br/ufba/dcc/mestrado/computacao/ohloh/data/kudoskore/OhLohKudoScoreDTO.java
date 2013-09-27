package br.ufba.dcc.mestrado.computacao.ohloh.data.kudoskore;

import java.sql.Timestamp;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OhLohKudoScoreDTO.NODE_NAME)
public class OhLohKudoScoreDTO implements OhLohResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4697647432356330723L;

	public final static String NODE_NAME = "kudo_score";

	@XStreamAsAttribute
	@XStreamConverter(value = NullableLongXStreamConverter.class)
	private Long id;

	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	@XStreamAlias("created_at")
	private Timestamp createdAt;

	@XStreamAlias("kudo_rank")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long kudoRank;

	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long position;

	@XStreamAlias("max_position")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long maxPosition;

	@XStreamAlias("position_delta")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long positionDelta;

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

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
