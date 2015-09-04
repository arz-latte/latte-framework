package at.arz.latte.framework.validator;

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
public class UrlValidator implements ConstraintValidator<Url, String> {

	@Override
	public void initialize(Url constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintContext) {

		if (value == null) {
			return false;
		}
		
		if (value.equals("#")) {
			return true;
		}

		try {
			new URL(value);
		} catch (MalformedURLException ex) {
			return false;
		}

		return true;
	}
}
