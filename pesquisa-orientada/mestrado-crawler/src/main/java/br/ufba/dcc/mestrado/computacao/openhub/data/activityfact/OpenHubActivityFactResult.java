package br.ufba.dcc.mestrado.computacao.openhub.data.activityfact;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubActivityFactResult implements OpenHubBaseResult {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3427208861341075797L;
	
	
	
	private List<OpenHubActivityFactDTO> activityFacts;
	
	@XmlElement(name = "activity_fact")
	public List<OpenHubActivityFactDTO> getActivityFacts() {
		return activityFacts;
	}
	
	public void setActivityFacts(List<OpenHubActivityFactDTO> activityFacts) {
		this.activityFacts = activityFacts;
	}
	
}
