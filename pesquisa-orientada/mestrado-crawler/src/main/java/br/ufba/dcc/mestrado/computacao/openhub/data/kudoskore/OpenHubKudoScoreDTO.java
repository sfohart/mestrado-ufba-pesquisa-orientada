package br.ufba.dcc.mestrado.computacao.openhub.data.kudoskore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubKudoScoreDTO.NODE_NAME)
public class OpenHubKudoScoreDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4697647432356330723L;

	public final static String NODE_NAME = "kudo_score";

	private Long kudoRank;

	private Long position;

	@XmlElement(name = "kudo_rank")
	public Long getKudoRank() {
		return kudoRank;
	}

	public void setKudoRank(Long kudoRank) {
		this.kudoRank = kudoRank;
	}

	@XmlElement(name = "position")
	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}


}
