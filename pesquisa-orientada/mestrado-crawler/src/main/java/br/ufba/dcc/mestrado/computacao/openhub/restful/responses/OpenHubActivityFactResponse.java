package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactResult;

@XmlRootElement(name = OpenHubActivityFactResponse.NODE_NAME)
public class OpenHubActivityFactResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8321627917816410282L;
	private OpenHubActivityFactResult result;
	
	
	@XmlElement(name = OpenHubActivityFactResult.NODE_NAME)
	public OpenHubActivityFactResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubActivityFactResult result) {
		this.result = result;
	}
	
}
