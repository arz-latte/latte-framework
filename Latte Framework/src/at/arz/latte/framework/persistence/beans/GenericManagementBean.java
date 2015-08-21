package at.arz.latte.framework.persistence.beans;

import java.util.HashMap;
import java.util.Set;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * generic bean for entity validation
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 * @param <AbstractEntity>
 */
@Stateful
//@Stateless & abstract
public class GenericManagementBean<AbstractEntity> {

	/**
	 * validates object, adds validation messages to it if invalid
	 * 
	 * @param module
	 * @return
	 */
	protected boolean validate(AbstractEntity entity) {

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<AbstractEntity>> constraintViolations = validator.validate(entity);

		if (constraintViolations.size() > 0) {
			HashMap<String, String> violationMessages = new HashMap<String, String>();

			for (ConstraintViolation<AbstractEntity> cv : constraintViolations) {
				violationMessages.put(cv.getPropertyPath().toString(), cv.getMessage());

				System.out.println(cv.getPropertyPath() + ": " + cv.getMessage());
			}

			((at.arz.latte.framework.modules.models.AbstractEntity) entity).setValidation(violationMessages);
			return false;
		}

		return true;
	}

}