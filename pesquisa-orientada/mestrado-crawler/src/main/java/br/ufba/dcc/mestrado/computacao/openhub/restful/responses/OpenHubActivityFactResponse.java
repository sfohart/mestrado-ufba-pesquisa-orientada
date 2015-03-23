package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubActivityFactResponse.NODE_NAME)
public class OpenHubActivityFactResponse extends OpenHubBaseResponse {

	private OpenHubActivityFactResult result;
	
	public OpenHubActivityFactResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubActivityFactResult result) {
		this.result = result;
	}
	
}
