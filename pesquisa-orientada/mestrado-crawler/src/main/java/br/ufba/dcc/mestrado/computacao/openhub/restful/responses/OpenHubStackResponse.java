package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.stack.OpenHubStackResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubStackResponse.NODE_NAME)
public class OpenHubStackResponse extends OpenHubBaseResponse {

	private OpenHubStackResult result;
	
	public OpenHubStackResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubStackResult result) {
		this.result = result;
	}
	
}
