package at.arz.latte.framework.persistence.beans;

import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.services.restful.service.ModuleValidationWebApplicationException;


public class ValidateBean {

	public static void validate(Module module) {		
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<Module>> constraintViolations = validator
				.validate(module);

		if (constraintViolations.size() > 0) {
			System.out.println("---------------------------------------");
			HashMap<String, String> violationMessages = new HashMap<String, String>();

			for (ConstraintViolation<Module> cv : constraintViolations) {
				violationMessages.put(cv.getPropertyPath().toString(),
						cv.getMessage());

				System.out.println(cv.getPropertyPath() + ": "
						+ cv.getMessage());
			}

			System.out.println("---------------------------------------");
			
		//	throw new ModuleValidationWebApplicationException(violationMessages, module);
		}
	}

}