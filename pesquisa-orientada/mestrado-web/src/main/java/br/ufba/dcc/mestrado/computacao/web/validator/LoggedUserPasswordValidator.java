package br.ufba.dcc.mestrado.computacao.web.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@Component(value = "loggedUserPasswordValidator")
public class LoggedUserPasswordValidator implements Validator {

	@Autowired
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private ResourceBundle bundle;
	
	public LoggedUserPasswordValidator() {
		this.bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.account");
	}
	

	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void validate(
			FacesContext context, 
			UIComponent component,
			Object value) throws ValidatorException {
		
		((HtmlInputSecret) component).setValid(true);
		
		String userPassword = (String) value;

		UserEntity loggedUser = getUserDetailsService().loadFullLoggedUser();
		
		if (loggedUser == null || ! getPasswordEncoder().matches(userPassword, loggedUser.getPassword())) {
		
			((HtmlInputSecret) component).setValid(false);
		
			String message = bundle.getString("account.userPassword.invalid.message");
			FacesMessage facesMessage = new FacesMessage(message);
			throw new ValidatorException(facesMessage);
		} 
		
	}

}
