package br.ufba.dcc.mestrado.computacao.openhub.data.kudo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubKudoResult implements OpenHubBaseResult {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5988299780741488163L;
	
	
	private List<OpenHubKudoDTO> kudos;
	
	@XmlElement(name = "kudo")
	public List<OpenHubKudoDTO> getKudos() {
		return kudos;
	}
	
	public void setKudos(List<OpenHubKudoDTO> kudos) {
		this.kudos = kudos;
	}

}
