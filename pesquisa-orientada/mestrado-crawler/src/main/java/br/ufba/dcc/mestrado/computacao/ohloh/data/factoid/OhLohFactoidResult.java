package br.ufba.dcc.mestrado.computacao.ohloh.data.factoid;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohFactoidResult {
	
	@XStreamImplicit(itemFieldName="factoid")
	private List<OhLohFactoidDTO> ohLohFactoids;
	
	public List<OhLohFactoidDTO> getOhLohFactoids() {
		return ohLohFactoids;
	}
	
	public void setOhLohFactoids(List<OhLohFactoidDTO> ohLohFactoids) {
		this.ohLohFactoids = ohLohFactoids;
	}
	
}
