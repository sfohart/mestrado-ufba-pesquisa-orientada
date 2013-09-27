package br.ufba.dcc.mestrado.computacao.ohloh.data.analysis;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("result")
public class OhLohAnalysisResult {

	private OhLohAnalysisDTO analysis;
	
	public OhLohAnalysisDTO getAnalysis() {
		return analysis;
	}
	
	public void setAnalysis(OhLohAnalysisDTO analysis) {
		this.analysis = analysis;
	}
	
}
