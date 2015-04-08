package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OpenHubProjectResponse.NODE_NAME)
public class OpenHubProjectResponse extends OpenHubBaseResponse {

	private OpenHubProjectResult result;
	
	public OpenHubProjectResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubProjectResult result) {
		this.result = result;
	}
	
}
