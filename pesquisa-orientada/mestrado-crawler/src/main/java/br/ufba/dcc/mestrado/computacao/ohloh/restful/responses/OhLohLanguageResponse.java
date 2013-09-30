package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohLanguageResponse.NODE_NAME)
public class OhLohLanguageResponse extends OhLohBaseResponse {

	private OhLohLanguageResult result;
	
	public OhLohLanguageResult getResult() {
		return result;
	}
	
	public void setResult(OhLohLanguageResult result) {
		this.result = result;
	}
	
}
