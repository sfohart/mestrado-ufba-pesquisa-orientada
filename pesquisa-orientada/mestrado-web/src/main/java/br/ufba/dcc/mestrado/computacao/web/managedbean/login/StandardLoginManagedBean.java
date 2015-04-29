
package br.ufba.dcc.mestrado.computacao.web.managedbean.login;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="loginMB")
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="loginMapping",
			beanName="loginMB", 
			pattern="/account/login",
			viewId="/login/login.jsf")
})
public class StandardLoginManagedBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 238087893391391911L;
	
	protected final static Logger logger = Logger.getLogger(StandardLoginManagedBean.class.getName());
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	private UserEntity loggedUser;
	
	public UserEntity getLoggedUser() {
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
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String signIn() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/account/authenticate");
				//.getRequestDispatcher("/j_spring_security_check");
		
		dispatcher.forward(
				((ServletRequest) context.getRequest()), 
				((ServletResponse) context.getResponse()));
		
		FacesContext.getCurrentInstance().responseComplete();
		
		return null;
	}
	
	public void verifyLocalSignIn(ComponentSystemEvent event) {
		Exception authenticationException = (Exception) FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		if (authenticationException != null) {
			logger.log(Level.WARNING, "Found exception in session map: " + authenticationException);
			
			if (authenticationException instanceof BadCredentialsException) {
				FacesContext.getCurrentInstance()
					.getExternalContext()
					.getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
					
				FacesContext.getCurrentInstance().addMessage(
						null, 
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR, 
								"Usu�rio ou senha inv�lidos", 
								"Usu�rio ou senha inv�lidos"));
			}
		}
	}
	
	
	
	public String signOut() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
		
		dispatcher.forward(
				((ServletRequest) context.getRequest()), 
				((ServletResponse) context.getResponse()));
		
		FacesContext.getCurrentInstance().responseComplete();
		
		return "/index.jsf";
	}
	

}

