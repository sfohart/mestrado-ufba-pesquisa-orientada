package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias(OhLohAnalysisResponse.NODE_NAME)
public class OhLohAnalysisResponse extends OhLohBaseResponse {

	private OhLohAnalysisResult result;

	public OhLohAnalysisResult getResult() {
		return result;
	}

	public void setResult(OhLohAnalysisResult result) {
		this.result = result;
	}

	
	
}
