package br.ufba.dcc.mestrado.computacao.ohloh.data.stack;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohStackResult {

	
	@XStreamImplicit(itemFieldName="stack")
	private List<OhLohStackDTO> stacks;
	
	public List<OhLohStackDTO> getStacks() {
		return stacks;
	}
	
	public void setStacks(List<OhLohStackDTO> stacks) {
		this.stacks = stacks;
	}

	
}
