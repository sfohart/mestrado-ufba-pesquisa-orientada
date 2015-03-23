package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.kudo.OpenHubKudoResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubKudoResponse.NODE_NAME)
public class OpenHubKudoResponse extends OpenHubBaseResponse {

	private OpenHubKudoResult result;
	
	public OpenHubKudoResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubKudoResult result) {
		this.result = result;
	}
	
}
