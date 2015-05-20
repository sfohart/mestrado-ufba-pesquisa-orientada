package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisResult;


@XmlRootElement(name = OpenHubAnalysisResponse.NODE_NAME)
public class OpenHubAnalysisResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3357967067494842326L;
	private OpenHubAnalysisResult result;

	@XmlElement(name = OpenHubAnalysisResult.NODE_NAME)
	public OpenHubAnalysisResult getResult() {
		return result;
	}

	public void setResult(OpenHubAnalysisResult result) {
		this.result = result;
	}

	
	
}
