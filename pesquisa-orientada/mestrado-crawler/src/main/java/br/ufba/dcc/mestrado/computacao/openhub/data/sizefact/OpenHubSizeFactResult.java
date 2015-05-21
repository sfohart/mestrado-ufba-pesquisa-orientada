package br.ufba.dcc.mestrado.computacao.openhub.data.sizefact;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubSizeFactResult implements OpenHubBaseResult  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 311374481006257199L;
	
	
	private List<OpenHubSizeFactDTO> sizeFacts;
	
	@XmlElement(name ="size_fact")
	public List<OpenHubSizeFactDTO> getSizeFacts() {
		return sizeFacts;
	}
	
	public void setSizeFacts(List<OpenHubSizeFactDTO> sizeFacts) {
		this.sizeFacts = sizeFacts;
	}
	
	
}
