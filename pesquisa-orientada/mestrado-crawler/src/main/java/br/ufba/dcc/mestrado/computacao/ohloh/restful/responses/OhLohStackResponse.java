package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohStackResponse.NODE_NAME)
public class OhLohStackResponse extends OhLohBaseResponse {

	private OhLohStackResult result;
	
	public OhLohStackResult getResult() {
		return result;
	}
	
	public void setResult(OhLohStackResult result) {
		this.result = result;
	}
	
}
