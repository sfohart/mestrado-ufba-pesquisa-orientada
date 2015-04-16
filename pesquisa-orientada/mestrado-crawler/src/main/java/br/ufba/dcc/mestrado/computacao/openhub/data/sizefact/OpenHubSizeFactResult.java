package br.ufba.dcc.mestrado.computacao.openhub.data.sizefact;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubSizeFactResult {

	
	@XStreamImplicit(itemFieldName="size_fact")
	private List<OpenHubSizeFactDTO> sizeFacts;
	
	public List<OpenHubSizeFactDTO> getSizeFacts() {
		return sizeFacts;
	}
	
	
	public void setSizeFacts(List<OpenHubSizeFactDTO> sizeFacts) {
		this.sizeFacts = sizeFacts;
	}
	
	
}
