package br.ufba.dcc.mestrado.computacao.ohloh.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;

@XStreamAlias(OhLohAffiliatedCommitterDTO.NODE_NAME)
public class OhLohAffiliatedCommitterDTO implements OhLohResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 453474440931984044L;
	
	public final static String NODE_NAME = "affiliator";
	
}
