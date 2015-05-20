package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectResult;

@XmlRootElement(name = OpenHubLanguageResponse.NODE_NAME)
public class OpenHubProjectResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3770845695889860194L;
	private OpenHubProjectResult result;
	
	
	@XmlElement(name = OpenHubProjectResult.NODE_NAME)
	public OpenHubProjectResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubProjectResult result) {
		this.result = result;
	}
	
}
