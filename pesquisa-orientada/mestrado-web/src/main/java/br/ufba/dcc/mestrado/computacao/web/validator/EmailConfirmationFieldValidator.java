package br.ufba.dcc.mestrado.computacao.web.validator;

import javax.faces.validator.FacesValidator;

@FacesValidator(value = "emailConfirmationFieldValidator")
public class EmailConfirmationFieldValidator extends DuplicatedFieldValidator {

	public EmailConfirmationFieldValidator() {
		super("email.confirmation.error.message");
	}

}
