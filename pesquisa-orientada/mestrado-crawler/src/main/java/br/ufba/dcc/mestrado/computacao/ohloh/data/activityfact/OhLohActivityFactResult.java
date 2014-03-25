package br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohActivityFactResult {

	
	@XStreamImplicit(itemFieldName="activity_fact")
	private List<OhLohActivityFactDTO> activityFacts;
	
	public List<OhLohActivityFactDTO> getActivityFacts() {
		return activityFacts;
	}
	
	public void setActivityFacts(List<OhLohActivityFactDTO> activityFacts) {
		this.activityFacts = activityFacts;
	}
	
}
