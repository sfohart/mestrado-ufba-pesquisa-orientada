package br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohActivityFactResult {

	
	@XStreamImplicit(itemFieldName="activity_fact")
	private List<OhLohActivityFactDTO> ohLohActivityFacts;
	
	public List<OhLohActivityFactDTO> getOhLohActivityFacts() {
		return ohLohActivityFacts;
	}
	
	public void setOhLohActivityFacts(List<OhLohActivityFactDTO> ohLohActivityFacts) {
		this.ohLohActivityFacts = ohLohActivityFacts;
	}
	
	
}
