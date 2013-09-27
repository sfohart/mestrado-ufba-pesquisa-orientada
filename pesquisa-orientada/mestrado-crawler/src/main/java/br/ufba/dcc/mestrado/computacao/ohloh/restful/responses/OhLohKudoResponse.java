package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.kudo.OhLohKudoResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohKudoResponse.NODE_NAME)
public class OhLohKudoResponse extends OhLohBaseResponse {

	private OhLohKudoResult result;
	
	public OhLohKudoResult getResult() {
		return result;
	}
	
	public void setResult(OhLohKudoResult result) {
		this.result = result;
	}
	
}
