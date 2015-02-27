package br.ufba.dcc.mestrado.computacao.ohloh.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;

@XStreamAlias(OhLohPortifolioProjectDTO.NODE_NAME)
public class OhLohPortifolioProjectDTO implements OhLohResultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6203597426768385191L;
	
	public final static String NODE_NAME = "project";

}
