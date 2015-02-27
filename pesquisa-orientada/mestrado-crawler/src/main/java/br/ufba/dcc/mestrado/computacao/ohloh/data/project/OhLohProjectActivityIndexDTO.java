package br.ufba.dcc.mestrado.computacao.ohloh.data.project;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;

@XStreamAlias(OhLohProjectActivityIndexDTO.NODE_NAME)
public class OhLohProjectActivityIndexDTO implements OhLohResultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2439053658378416084L;

	public final static String NODE_NAME = "project_activity_index";
	
	private Long value;
	
	private String description;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
