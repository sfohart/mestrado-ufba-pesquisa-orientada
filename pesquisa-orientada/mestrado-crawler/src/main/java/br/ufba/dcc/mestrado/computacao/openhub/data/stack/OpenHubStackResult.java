package br.ufba.dcc.mestrado.computacao.openhub.data.stack;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubStackResult implements OpenHubBaseResult {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2147917161287350580L;
	
	
	private List<OpenHubStackDTO> stacks;
	
	@XmlElement(name = "stack")
	public List<OpenHubStackDTO> getStacks() {
		return stacks;
	}
	
	public void setStacks(List<OpenHubStackDTO> stacks) {
		this.stacks = stacks;
	}

	
}
