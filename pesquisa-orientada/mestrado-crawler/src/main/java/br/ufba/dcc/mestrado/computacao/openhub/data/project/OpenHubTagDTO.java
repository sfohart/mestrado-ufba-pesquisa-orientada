package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;


@XmlRootElement(name = OpenHubTagDTO.NODE_NAME)
public class OpenHubTagDTO implements OpenHubResultDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3111939284107189941L;

	public final static String NODE_NAME = "tag";

	private String name;

	@XmlValue
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
