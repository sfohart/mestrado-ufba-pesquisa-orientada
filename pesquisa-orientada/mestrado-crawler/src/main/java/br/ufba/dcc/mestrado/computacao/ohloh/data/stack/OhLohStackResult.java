package br.ufba.dcc.mestrado.computacao.ohloh.data.stack;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohStackResult {

	
	@XStreamImplicit(itemFieldName="stack")
	private List<OhLohStackDTO> ohLohStacks;
	
	public List<OhLohStackDTO> getOhLohStacks() {
		return ohLohStacks;
	}
	
	public void setOhLohStacks(List<OhLohStackDTO> ohLohStacks) {
		this.ohLohStacks = ohLohStacks;
	}
	
	
}
