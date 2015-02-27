package br.ufba.dcc.mestrado.computacao.ohloh.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;

@XStreamAlias(OhLohOutsideProjectDTO.NODE_NAME)
public class OhLohOutsideProjectDTO implements OhLohResultDTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1829045620322612491L;
	
	public final static String NODE_NAME = "project";
	
}
