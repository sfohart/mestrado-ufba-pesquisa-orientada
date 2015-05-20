package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageResult;

@XmlRootElement(name = OpenHubLanguageResponse.NODE_NAME)
public class OpenHubLanguageResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7894981676768160275L;
	
	
	private OpenHubLanguageResult result;
	
	public OpenHubLanguageResponse() {
		super();
	}
	
	@XmlElement(name = OpenHubLanguageResult.NODE_NAME)
	public OpenHubLanguageResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubLanguageResult result) {
		this.result = result;
	}
	
}
