package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.factoid.OpenHubFactoidResult;

@XmlRootElement(name = OpenHubFactoidResponse.NODE_NAME)
public class OpenHubFactoidResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4890390355109966899L;
	private OpenHubFactoidResult result;
	
	@XmlElement(name = OpenHubFactoidResult.NODE_NAME)
	public OpenHubFactoidResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubFactoidResult result) {
		this.result = result;
	}
	
}
