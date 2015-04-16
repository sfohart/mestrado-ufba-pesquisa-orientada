package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.OpenHubTagDTOXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(OpenHubTagDTO.NODE_NAME)
@XStreamConverter(value = OpenHubTagDTOXStreamConverter.class)
public class OpenHubTagDTO implements OpenHubResultDTO {
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
