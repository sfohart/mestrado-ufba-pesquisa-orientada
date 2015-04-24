package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubLanguageResponse.NODE_NAME)
public class OpenHubLanguageResponse extends OpenHubBaseResponse {

	private OpenHubLanguageResult result;
	
	public OpenHubLanguageResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubLanguageResult result) {
		this.result = result;
	}
	
}
