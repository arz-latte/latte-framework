package at.arz.latte.framework.persistence.beans;

import java.util.HashMap;
import java.util.Set;

import javax.ejb.Stateful;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import at.arz.latte.framework.modules.models.Module;

@Stateful
public class GenericManagementBean<T> {

	/**
	 * validates object, adds validation messages to it if invalid
	 * 
	 * @param module
	 * @return
	 */
	protected T validate(T object) {

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

		if (constraintViolations.size() > 0) {
			HashMap<String, String> violationMessages = new HashMap<String, String>();

			for (ConstraintViolation<T> cv : constraintViolations) {
				violationMessages.put(cv.getPropertyPath().toString(), cv.getMessage());

				System.out.println(cv.getPropertyPath() + ": " + cv.getMessage());
			}

			((Module) object).setValidation(violationMessages);
		}

		return object;
	}

}