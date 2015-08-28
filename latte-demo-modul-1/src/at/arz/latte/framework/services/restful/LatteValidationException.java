package at.arz.latte.framework.services.restful;

import java.util.HashMap;
import java.util.Set;

import javax.ejb.ApplicationException;
import javax.validation.ConstraintViolation;

@ApplicationException(rollback = true)
public class LatteValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private HashMap<String, String> validation;

	private int status;

	public LatteValidationException(int status, Set<ConstraintViolation<Object>> violations) {
		super();
		this.status = status;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HashMap<String, String> getValidation() {
		return validation;
	}

	public void setValidation(HashMap<String, String> validation) {
		this.validation = validation;
	}

}
