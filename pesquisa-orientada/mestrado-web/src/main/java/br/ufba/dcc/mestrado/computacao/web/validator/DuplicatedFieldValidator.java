package br.ufba.dcc.mestrado.computacao.web.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public abstract class DuplicatedFieldValidator implements Validator {
	
	private final static String BUNDLE_NAME = "messages";
	
	private String messageKey;
	private ResourceBundle bundle;
	
	
	public DuplicatedFieldValidator(String messageKey) {
		//Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		FacesContext context = FacesContext.getCurrentInstance(); 
		
		this.bundle = context.getApplication().getResourceBundle(context, BUNDLE_NAME);
		this.messageKey = messageKey;
	}

	@Override
	public void validate(
			FacesContext context, 
			UIComponent component,
			Object value) throws ValidatorException {

		String stringToConfirm = (String) value;
		
		UIInput uiInput = (UIInput) component.getAttributes().get("match");
		String stringParam = (String) uiInput.getValue();
		
		if (stringParam != null && ! stringParam.equals(stringToConfirm)) {
			String message = bundle.getString(messageKey);
			
			FacesMessage facesMessage = new FacesMessage(message);
			throw new ValidatorException(facesMessage);
		}
		
	}

}
