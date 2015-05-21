package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact.OpenHubContributorFactResult;

@XmlRootElement(name = OpenHubContributorFactResponse.NODE_NAME)
public class OpenHubContributorFactResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2001200467102879892L;
	private OpenHubContributorFactResult result;
	
	@XmlElement(name = OpenHubContributorFactResult.NODE_NAME)
	public OpenHubContributorFactResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubContributorFactResult result) {
		this.result = result;
	}
	
}
