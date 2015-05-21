package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.sizefact.OpenHubSizeFactResult;

@XmlRootElement(name = OpenHubSizeFactResponse.NODE_NAME)
public class OpenHubSizeFactResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4447351117324480533L;
	private OpenHubSizeFactResult result;
	
	
	@XmlElement(name = OpenHubSizeFactResult.NODE_NAME)
	public OpenHubSizeFactResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubSizeFactResult result) {
		this.result = result;
	}
	
}
