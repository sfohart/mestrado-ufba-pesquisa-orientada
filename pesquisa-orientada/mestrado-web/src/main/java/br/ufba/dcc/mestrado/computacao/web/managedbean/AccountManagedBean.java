package br.ufba.dcc.mestrado.computacao.web.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAccountService;

@ManagedBean(name="accountMB")
@ViewScoped
public class AccountManagedBean {

	@ManagedProperty("#{ohLohAccountService}")
	private OhLohAccountService ohLohAccountService;
	
	private OhLohAccountEntity account;
	
	public AccountManagedBean() {
		this.account = new OhLohAccountEntity();
	}
	
	public OhLohAccountService getOhLohAccountService() {
		return ohLohAccountService;
	}
	
	public void setOhLohAccountService(OhLohAccountService ohLohAccountService) {
		this.ohLohAccountService = ohLohAccountService;
	}
	
	public OhLohAccountEntity getAccount() {
		return account;
	}
	
	public void setAccount(OhLohAccountEntity account) {
		this.account = account;
	}
	
	public String createAccount() {
		return null;
	}
	
}
