package br.ufba.dcc.mestrado.computacao.ohloh.data.account;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("result")
public class OhLohAccountResult {
	
	@XStreamImplicit(itemFieldName="account")
	private List<OhLohAccountDTO> ohLohAccounts;
	
	public List<OhLohAccountDTO> getOhLohAccounts() {
		return ohLohAccounts;
	}
	
	public void setOhLohAccounts(List<OhLohAccountDTO> ohLohAccounts) {
		this.ohLohAccounts = ohLohAccounts;
	}
	
	
}
