package br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubContributorFactResult {

	
	@XStreamImplicit(itemFieldName="contributor_fact")
	private List<OpenHubContributorFactDTO> contributorFacts;
	
	public List<OpenHubContributorFactDTO> getContributorFacts() {
		return contributorFacts;
	}
	
	public void setContributorFacts(
			List<OpenHubContributorFactDTO> contributorFacts) {
		this.contributorFacts = contributorFacts;
	}
	
}
