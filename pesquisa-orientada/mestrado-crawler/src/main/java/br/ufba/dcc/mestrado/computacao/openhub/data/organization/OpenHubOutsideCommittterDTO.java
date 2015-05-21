package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubOutsideCommittterDTO.NODE_NAME)
public class OpenHubOutsideCommittterDTO implements OpenHubResultDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5933831548007744703L;
	
	public final static String NODE_NAME = "contributor";

}
