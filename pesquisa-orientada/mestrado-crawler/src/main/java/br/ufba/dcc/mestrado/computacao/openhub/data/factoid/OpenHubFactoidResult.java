package br.ufba.dcc.mestrado.computacao.openhub.data.factoid;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubFactoidResult implements OpenHubBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2896599792980288548L;
	
	
	private List<OpenHubFactoidDTO> factoids;
	
	@XmlElement(name = "factoid")
	public List<OpenHubFactoidDTO> getFactoids() {
		return factoids;
	}
	
	public void setFactoids(List<OpenHubFactoidDTO> factoids) {
		this.factoids = factoids;
	}
	
}
