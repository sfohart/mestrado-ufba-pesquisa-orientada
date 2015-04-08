package br.ufba.dcc.mestrado.computacao.openhub.data.activityfact;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubActivityFactResult {

	
	@XStreamImplicit(itemFieldName="activity_fact")
	private List<OpenHubActivityFactDTO> activityFacts;
	
	public List<OpenHubActivityFactDTO> getActivityFacts() {
		return activityFacts;
	}
	
	public void setActivityFacts(List<OpenHubActivityFactDTO> activityFacts) {
		this.activityFacts = activityFacts;
	}
	
}
