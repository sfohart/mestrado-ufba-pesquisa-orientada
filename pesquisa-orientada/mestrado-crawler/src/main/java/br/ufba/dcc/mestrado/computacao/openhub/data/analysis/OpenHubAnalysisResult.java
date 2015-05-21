package br.ufba.dcc.mestrado.computacao.openhub.data.analysis;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubAnalysisResult implements OpenHubBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7775842708109166229L;

	private List<OpenHubAnalysisDTO> analises;
	
	@XmlElement(name = "analysis")
	public List<OpenHubAnalysisDTO> getAnalises() {
		return analises;
	}
	
	public void setAnalises(List<OpenHubAnalysisDTO> analises) {
		this.analises = analises;
	}
	
}
