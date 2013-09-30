package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohAccountResponse.NODE_NAME)
public class OhLohAccountResponse extends OhLohBaseResponse {

	private OhLohAccountResult result;
	
	public OhLohAccountResult getResult() {
		return result;
	}
	
	public void setResult(OhLohAccountResult result) {
		this.result = result;
	}
	
}
