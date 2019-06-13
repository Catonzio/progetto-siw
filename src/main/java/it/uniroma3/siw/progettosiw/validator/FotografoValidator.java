package it.uniroma3.siw.progettosiw.validator;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progettosiw.model.Fotografo;

@Controller
public class FotografoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Fotografo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");
	}

}
