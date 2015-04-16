package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.factoid.OpenHubFactoidResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubFactoidResponse.NODE_NAME)
public class OpenHubFactoidResponse extends OpenHubBaseResponse {

	private OpenHubFactoidResult result;
	
	public OpenHubFactoidResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubFactoidResult result) {
		this.result = result;
	}
	
}
