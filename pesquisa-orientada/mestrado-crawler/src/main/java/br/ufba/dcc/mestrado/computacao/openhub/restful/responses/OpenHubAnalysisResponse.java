package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias(OpenHubAnalysisResponse.NODE_NAME)
public class OpenHubAnalysisResponse extends OpenHubBaseResponse {

	private OpenHubAnalysisResult result;

	public OpenHubAnalysisResult getResult() {
		return result;
	}

	public void setResult(OpenHubAnalysisResult result) {
		this.result = result;
	}

	
	
}
