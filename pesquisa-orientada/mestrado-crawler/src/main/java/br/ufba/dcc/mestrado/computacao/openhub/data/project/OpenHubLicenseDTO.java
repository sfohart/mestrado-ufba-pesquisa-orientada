package br.ufba.dcc.mestrado.computacao.openhub.data.project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubLicenseDTO.NODE_NAME)
public class OpenHubLicenseDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1612821573723959553L;

	public final static String NODE_NAME = "license";
	
	private String id;

	private String name;

	private String niceName;

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "nice_name")
	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}
	
	@XmlID
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
