package br.ufba.dcc.mestrado.computacao.openhub.data.analysis;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("result")
public class OpenHubAnalysisResult {

	private OpenHubAnalysisDTO analysis;
	
	public OpenHubAnalysisDTO getAnalysis() {
		return analysis;
	}
	
	public void setAnalysis(OpenHubAnalysisDTO analysis) {
		this.analysis = analysis;
	}
	
}
