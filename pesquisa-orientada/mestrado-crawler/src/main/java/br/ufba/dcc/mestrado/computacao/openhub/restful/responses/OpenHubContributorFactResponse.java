package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact.OpenHubContributorFactResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubContributorFactResponse.NODE_NAME)
public class OpenHubContributorFactResponse extends OpenHubBaseResponse {

	private OpenHubContributorFactResult result;
	
	public OpenHubContributorFactResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubContributorFactResult result) {
		this.result = result;
	}
	
}
