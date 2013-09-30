package br.ufba.dcc.mestrado.computacao.ohloh.data.project;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.OhLohTagDTOXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(OhLohTagDTO.NODE_NAME)
@XStreamConverter(value = OhLohTagDTOXStreamConverter.class)
public class OhLohTagDTO implements OhLohResultDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3111939284107189941L;

	public final static String NODE_NAME = "tag";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
