package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.kudo.OpenHubKudoResult;

@XmlRootElement(name = OpenHubKudoResponse.NODE_NAME)
public class OpenHubKudoResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8466956196390788609L;
	private OpenHubKudoResult result;
	
	@XmlElement(name = OpenHubKudoResult.NODE_NAME)
	public OpenHubKudoResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubKudoResult result) {
		this.result = result;
	}
	
}
