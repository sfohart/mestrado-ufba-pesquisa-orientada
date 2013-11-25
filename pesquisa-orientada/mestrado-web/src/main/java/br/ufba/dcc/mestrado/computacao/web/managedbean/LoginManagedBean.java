package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@ManagedBean(name="loginMB")
@ViewScoped
public class LoginManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 238087893391391911L;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	private OhLohAccountEntity loggedUser;
	
	public OhLohAccountEntity getLoggedUser() {
		if (this.loggedUser == null) {
			this.loggedUser = getUserDetailsService().loadFullLoggedUser();
		}
		
		return this.loggedUser;
	}
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public String signIn() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
		
		dispatcher.forward(
				((ServletRequest) context.getRequest()), 
				((ServletResponse) context.getResponse()));
		
		FacesContext.getCurrentInstance().responseComplete();
		
		return null;
	}
	
	public String signOut() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
		
		dispatcher.forward(
				((ServletRequest) context.getRequest()), 
				((ServletResponse) context.getResponse()));
		
		FacesContext.getCurrentInstance().responseComplete();
		
		return null;
	}
	

}
