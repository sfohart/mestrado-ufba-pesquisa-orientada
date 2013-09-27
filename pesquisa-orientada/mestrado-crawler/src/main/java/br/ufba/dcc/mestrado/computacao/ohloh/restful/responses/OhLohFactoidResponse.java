package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.factoid.OhLohFactoidResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohFactoidResponse.NODE_NAME)
public class OhLohFactoidResponse extends OhLohBaseResponse {

	private OhLohFactoidResult result;
	
	public OhLohFactoidResult getResult() {
		return result;
	}
	
	public void setResult(OhLohFactoidResult result) {
		this.result = result;
	}
	
}
