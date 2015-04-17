package br.ufba.dcc.mestrado.computacao.web.validator;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {
	
	private final static String BUNDLE_NAME = "messages";
	
	private Pattern emailPattern = Pattern.compile("([\\w]+)([\\.\\w]*)@([\\w]+)([\\.\\w]*)");
	private ResourceBundle bundle;
	
	public EmailValidator() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		this.bundle = context.getApplication().getResourceBundle(context, BUNDLE_NAME);
	}

	@Override
	public void validate(
			FacesContext context, 
			UIComponent component,
			Object value) throws ValidatorException {

		String emailValue = (String) value;
		Matcher matcher = emailPattern.matcher(emailValue);
		
		if (! matcher.matches()) {
			String message = bundle.getString("email.invalid.message");
			FacesMessage facesMessage = new FacesMessage(message);
			throw new ValidatorException(facesMessage);
		}
		
		
	}

}
