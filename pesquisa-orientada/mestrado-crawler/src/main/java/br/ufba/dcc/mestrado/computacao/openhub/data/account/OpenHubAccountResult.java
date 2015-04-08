package br.ufba.dcc.mestrado.computacao.openhub.data.account;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OpenHubAccountResult {
	
	@XStreamImplicit(itemFieldName="account")
	private List<OpenHubAccountDTO> accounts;
	
	public List<OpenHubAccountDTO> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<OpenHubAccountDTO> accounts) {
		this.accounts = accounts;
	}
	
}
