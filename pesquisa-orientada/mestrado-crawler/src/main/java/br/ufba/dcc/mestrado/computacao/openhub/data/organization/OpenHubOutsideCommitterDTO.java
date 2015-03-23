package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XStreamAlias(OpenHubOutsideCommitterDTO.NODE_NAME)
public class OpenHubOutsideCommitterDTO implements OpenHubResultDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5933831548007744703L;
	
	public final static String NODE_NAME = "contributor";

}
