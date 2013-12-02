package br.ufba.dcc.mestrado.computacao.web.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

@Component
public class LoginPhaseListener implements PhaseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7872214166251281230L;
	
	protected final static Logger logger = Logger.getLogger(LoginPhaseListener.class.getName());
	
	
	/**
	 * Verifica o login via Facebook, Twitter, etc
	 */
	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {
		verifyLocalSignIn();
	}
	
	
	/**
	 * Exibe mensagem no caso de login interno falhar
	 */
	protected void verifyLocalSignIn() {
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
								"Usuário ou senha inválidos", 
								"Usuário ou senha inválidos"));
			}
		}
	}

	/**
	 * Em qual phase do lifecycle do JSF os métodos devem ser executados?
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
	
}
