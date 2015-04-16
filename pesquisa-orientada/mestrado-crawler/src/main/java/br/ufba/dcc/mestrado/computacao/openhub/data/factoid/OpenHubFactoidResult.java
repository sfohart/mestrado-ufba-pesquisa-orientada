package br.ufba.dcc.mestrado.computacao.openhub.data.factoid;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubFactoidResult {
	
	@XStreamImplicit(itemFieldName="factoid")
	private List<OpenHubFactoidDTO> factoids;
	
	public List<OpenHubFactoidDTO> getFactoids() {
		return factoids;
	}
	
	public void setFactoids(List<OpenHubFactoidDTO> factoids) {
		this.factoids = factoids;
	}
	
}
