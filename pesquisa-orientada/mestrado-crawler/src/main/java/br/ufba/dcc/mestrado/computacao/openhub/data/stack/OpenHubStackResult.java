package br.ufba.dcc.mestrado.computacao.openhub.data.stack;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubStackResult {

	
	@XStreamImplicit(itemFieldName="stack")
	private List<OpenHubStackDTO> stacks;
	
	public List<OpenHubStackDTO> getStacks() {
		return stacks;
	}
	
	public void setStacks(List<OpenHubStackDTO> stacks) {
		this.stacks = stacks;
	}

	
}
