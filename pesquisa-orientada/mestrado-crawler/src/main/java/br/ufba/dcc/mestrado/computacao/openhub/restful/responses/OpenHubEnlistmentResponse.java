package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.enlistment.OpenHubEnlistmentResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectResult;

@XmlRootElement(name = OpenHubEnlistmentResponse.NODE_NAME)
public class OpenHubEnlistmentResponse extends OpenHubBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6129675058260347424L;
	
	private OpenHubEnlistmentResult result;
	
	@XmlElement(name = OpenHubEnlistmentResult.NODE_NAME)
	public OpenHubEnlistmentResult getResult() {
		return result;
	}
	
	public void setResult(OpenHubEnlistmentResult result) {
		this.result = result;
	}
	
}
