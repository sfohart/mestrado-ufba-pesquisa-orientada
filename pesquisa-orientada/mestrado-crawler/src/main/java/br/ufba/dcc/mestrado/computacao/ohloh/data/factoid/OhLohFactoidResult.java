package br.ufba.dcc.mestrado.computacao.ohloh.data.factoid;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohFactoidResult {
	
	@XStreamImplicit(itemFieldName="factoid")
	private List<OhLohFactoidDTO> factoids;
	
	public List<OhLohFactoidDTO> getFactoids() {
		return factoids;
	}
	
	public void setFactoids(List<OhLohFactoidDTO> factoids) {
		this.factoids = factoids;
	}
	
}
