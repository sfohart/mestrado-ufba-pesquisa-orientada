package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.enlistment.OpenHubEnlistmentResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubEnlistmentResponse.NODE_NAME)
public class OpenHubEnlistmentResponse extends OpenHubBaseResponse {

	private OpenHubEnlistmentResult result;
	
	public OpenHubEnlistmentResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubEnlistmentResult result) {
		this.result = result;
	}
	
}
