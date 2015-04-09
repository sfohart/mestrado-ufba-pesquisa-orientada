package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XStreamAlias(OpenHubPortifolioProjectDTO.NODE_NAME)
public class OpenHubPortifolioProjectDTO implements OpenHubResultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6203597426768385191L;
	
	public final static String NODE_NAME = "project";

}
