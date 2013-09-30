package br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohSizeFactResult {

	
	@XStreamImplicit(itemFieldName="size_fact")
	private List<OhLohSizeFactDTO> ohLohSizeFacts;
	
	public List<OhLohSizeFactDTO> getOhLohSizeFacts() {
		return ohLohSizeFacts;
	}
	
	public void setOhLohSizeFacts(List<OhLohSizeFactDTO> ohLohSizeFacts) {
		this.ohLohSizeFacts = ohLohSizeFacts;
	}
	
	
}
