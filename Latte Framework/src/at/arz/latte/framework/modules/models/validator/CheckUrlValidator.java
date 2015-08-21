package at.arz.latte.framework.modules.models.validator;

import java.net.MalformedURLException;
import java.net.URL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * url validator
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class CheckUrlValidator implements ConstraintValidator<CheckUrl, String> {


	@Override
	public void initialize(CheckUrl constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {

		if (object == null)
			return false;

		try {
			new URL(object);
		} catch (MalformedURLException ex) {
			return false;
		}

		return true;
	}
}
