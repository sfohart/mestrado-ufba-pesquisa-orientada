package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XStreamAlias(OpenHubOutsideProjectDTO.NODE_NAME)
public class OpenHubOutsideProjectDTO implements OpenHubResultDTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1829045620322612491L;
	
	public final static String NODE_NAME = "project";
	
}
