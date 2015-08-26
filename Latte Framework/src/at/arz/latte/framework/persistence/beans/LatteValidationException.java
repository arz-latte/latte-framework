package at.arz.latte.framework.persistence.beans;

import java.util.HashMap;
import java.util.Set;

import javax.ejb.ApplicationException;
import javax.validation.ConstraintViolation;

@ApplicationException(rollback = true)
public class LatteValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private HashMap<String, String> validation;

	public LatteValidationException(Set<ConstraintViolation<Object>> violations) {
		super();
		convertToMap(violations);
	}

	private void convertToMap(Set<ConstraintViolation<Object>> violations) {

		if (violations.size() > 0) {
			validation = new HashMap<String, String>();

			for (ConstraintViolation<Object> cv : violations) {
				validation.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
		}
	}

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

}
