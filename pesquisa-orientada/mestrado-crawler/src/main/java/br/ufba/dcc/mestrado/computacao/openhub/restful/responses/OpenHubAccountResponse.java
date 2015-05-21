package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountResult;

@XmlRootElement(name = OpenHubAccountResponse.NODE_NAME)
public class OpenHubAccountResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7165931314073719944L;
	
	private OpenHubAccountResult result;
	
	@XmlElement(name = OpenHubAccountResult.NODE_NAME)
	public OpenHubAccountResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubAccountResult result) {
		this.result = result;
	}
	
}
