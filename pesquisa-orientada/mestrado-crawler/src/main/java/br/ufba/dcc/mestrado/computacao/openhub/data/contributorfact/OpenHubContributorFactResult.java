package br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubContributorFactResult implements OpenHubBaseResult {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5627330615888759844L;
	
	
	private List<OpenHubContributorFactDTO> contributorFacts;
	
	@XmlElement(name = "contributor_fact")
	public List<OpenHubContributorFactDTO> getContributorFacts() {
		return contributorFacts;
	}
	
	public void setContributorFacts(
			List<OpenHubContributorFactDTO> contributorFacts) {
		this.contributorFacts = contributorFacts;
	}
	
}
