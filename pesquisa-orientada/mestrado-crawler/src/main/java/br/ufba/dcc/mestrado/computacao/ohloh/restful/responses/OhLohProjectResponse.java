package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(OhLohProjectResponse.NODE_NAME)
public class OhLohProjectResponse extends OhLohBaseResponse {

	private OhLohProjectResult result;
	
	public OhLohProjectResult getResult() {
		return result;
	}
	
	public void setResult(OhLohProjectResult result) {
		this.result = result;
	}
	
}
