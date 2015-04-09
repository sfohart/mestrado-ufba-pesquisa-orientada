package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XStreamAlias(OpenHubAffiliatedCommitterDTO.NODE_NAME)
public class OpenHubAffiliatedCommitterDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 453474440931984044L;
	
	public final static String NODE_NAME = "affiliator";
	
}
