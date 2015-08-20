package at.arz.latte.framework.persistence.beans;

import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import at.arz.latte.framework.modules.dta.ResultData;
import at.arz.latte.framework.modules.models.Module;

public class ValidateBean {

	public static ResultData validate(Module module) {
		ResultData result = new ResultData();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Module>> constraintViolations = validator.validate(module);

		if (constraintViolations.size() > 0) {
			HashMap<String, String> violationMessages = new HashMap<String, String>();

			for (ConstraintViolation<Module> cv : constraintViolations) {
				violationMessages.put(cv.getPropertyPath().toString(), cv.getMessage());

				System.out.println(cv.getPropertyPath() + ": " + cv.getMessage());
			}

			result.setValidation(violationMessages);
		}
		
		return result;
	}

}