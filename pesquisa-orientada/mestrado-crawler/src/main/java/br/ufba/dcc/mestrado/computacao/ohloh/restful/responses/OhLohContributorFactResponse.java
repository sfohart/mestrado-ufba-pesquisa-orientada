package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.contributorfact.OhLohContributorFactResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohContributorFactResponse.NODE_NAME)
public class OhLohContributorFactResponse extends OhLohBaseResponse {

	private OhLohContributorFactResult result;
	
	public OhLohContributorFactResult getResult() {
		return result;
	}
	
	public void setResult(OhLohContributorFactResult result) {
		this.result = result;
	}
	
}
