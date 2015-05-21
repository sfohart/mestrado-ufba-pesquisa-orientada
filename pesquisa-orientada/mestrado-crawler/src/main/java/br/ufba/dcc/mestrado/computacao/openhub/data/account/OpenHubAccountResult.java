package br.ufba.dcc.mestrado.computacao.openhub.data.account;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubBaseResult;

@XmlRootElement(name = OpenHubBaseResult.NODE_NAME)
public class OpenHubAccountResult implements OpenHubBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6370873125004948140L;
	
	
	private List<OpenHubAccountDTO> accounts;
	
	@XmlElement(name = "account")
	public List<OpenHubAccountDTO> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<OpenHubAccountDTO> accounts) {
		this.accounts = accounts;
	}
	
}
