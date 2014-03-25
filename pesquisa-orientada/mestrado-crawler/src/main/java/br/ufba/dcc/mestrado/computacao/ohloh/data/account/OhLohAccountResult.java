package br.ufba.dcc.mestrado.computacao.ohloh.data.account;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohAccountResult {
	
	@XStreamImplicit(itemFieldName="account")
	private List<OhLohAccountDTO> accounts;
	
	public List<OhLohAccountDTO> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<OhLohAccountDTO> accounts) {
		this.accounts = accounts;
	}
	
}
