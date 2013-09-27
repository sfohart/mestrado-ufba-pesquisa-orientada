package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohSizeFactResponse.NODE_NAME)
public class OhLohSizeFactResponse extends OhLohBaseResponse {

	private OhLohSizeFactResult result;
	
	public OhLohSizeFactResult getResult() {
		return result;
	}
	
	public void setResult(OhLohSizeFactResult result) {
		this.result = result;
	}
	
}
