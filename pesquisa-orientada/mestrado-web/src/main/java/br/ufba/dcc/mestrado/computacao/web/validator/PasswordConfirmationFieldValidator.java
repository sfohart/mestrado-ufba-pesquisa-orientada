package br.ufba.dcc.mestrado.computacao.web.validator;

import javax.faces.validator.FacesValidator;

@FacesValidator(value = "passwordConfirmationFieldValidator")
public class PasswordConfirmationFieldValidator extends DuplicatedFieldValidator {

	public PasswordConfirmationFieldValidator() {
		super("password.confirmation.error.message");
	}

}
