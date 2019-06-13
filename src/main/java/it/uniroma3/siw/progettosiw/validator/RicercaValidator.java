package it.uniroma3.siw.progettosiw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progettosiw.support.Ricerca;

@Component
public class RicercaValidator implements Validator {

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stringa1", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stringa2", "required");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Ricerca.class.equals(clazz);
	}

}
