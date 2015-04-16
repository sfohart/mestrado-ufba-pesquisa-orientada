package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.sizefact.OpenHubSizeFactResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubSizeFactResponse.NODE_NAME)
public class OpenHubSizeFactResponse extends OpenHubBaseResponse {

	private OpenHubSizeFactResult result;
	
	public OpenHubSizeFactResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubSizeFactResult result) {
		this.result = result;
	}
	
}
