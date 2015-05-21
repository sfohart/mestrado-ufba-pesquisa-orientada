package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.stack.OpenHubStackResult;

@XmlRootElement(name = OpenHubStackResponse.NODE_NAME)
public class OpenHubStackResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 88031068249967236L;
	private OpenHubStackResult result;
	
	
	@XmlElement(name = OpenHubStackResult.NODE_NAME)
	public OpenHubStackResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubStackResult result) {
		this.result = result;
	}
	
}
