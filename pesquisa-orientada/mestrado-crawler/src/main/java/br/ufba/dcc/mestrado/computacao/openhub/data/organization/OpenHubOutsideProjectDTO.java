package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubOutsideProjectDTO.NODE_NAME)
public class OpenHubOutsideProjectDTO implements OpenHubResultDTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1829045620322612491L;
	
	public final static String NODE_NAME = "project";
	
}
