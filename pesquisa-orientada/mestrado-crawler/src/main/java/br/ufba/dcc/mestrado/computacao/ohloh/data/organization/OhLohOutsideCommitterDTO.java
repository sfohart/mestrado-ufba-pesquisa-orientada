package br.ufba.dcc.mestrado.computacao.ohloh.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;

@XStreamAlias(OhLohOutsideCommitterDTO.NODE_NAME)
public class OhLohOutsideCommitterDTO implements OhLohResultDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5933831548007744703L;
	
	public final static String NODE_NAME = "contributor";

}
