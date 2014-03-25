package br.ufba.dcc.mestrado.computacao.ohloh.data.contributorfact;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohContributorFactResult {

	
	@XStreamImplicit(itemFieldName="contributor_fact")
	private List<OhLohContributorFactDTO> contributorFacts;
	
	public List<OhLohContributorFactDTO> getContributorFacts() {
		return contributorFacts;
	}
	
	public void setContributorFacts(
			List<OhLohContributorFactDTO> contributorFacts) {
		this.contributorFacts = contributorFacts;
	}
	
}
