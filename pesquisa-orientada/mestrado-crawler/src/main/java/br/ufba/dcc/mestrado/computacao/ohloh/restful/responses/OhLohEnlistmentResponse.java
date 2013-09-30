package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohEnlistmentResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohEnlistmentResponse.NODE_NAME)
public class OhLohEnlistmentResponse extends OhLohBaseResponse {

	private OhLohEnlistmentResult result;
	
	public OhLohEnlistmentResult getResult() {
		return result;
	}
	
	public void setResult(OhLohEnlistmentResult result) {
		this.result = result;
	}
	
}
