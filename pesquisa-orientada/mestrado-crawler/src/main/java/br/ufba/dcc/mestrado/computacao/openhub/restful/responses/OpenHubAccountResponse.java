package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubAccountResponse.NODE_NAME)
public class OpenHubAccountResponse extends OpenHubBaseResponse {

	private OpenHubAccountResult result;
	
	public OpenHubAccountResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubAccountResult result) {
		this.result = result;
	}
	
}
