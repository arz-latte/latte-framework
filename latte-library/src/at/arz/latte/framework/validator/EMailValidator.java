package at.arz.latte.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * url validator
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class EMailValidator implements ConstraintValidator<EMail, String> {

	@Override
	public void initialize(EMail constraintAnnotation) {
	}

	@Override
	public boolean isValid(	String value,
							ConstraintValidatorContext constraintContext) {

		return value != null && value.matches("[\\w|-]+@\\w[\\w|-]*\\.[a-z]{2,3}");
	}
}
