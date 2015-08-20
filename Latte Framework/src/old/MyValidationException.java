package old;

import java.util.HashMap;

public class MyValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HashMap<String, String> violationMessages;

	public MyValidationException(HashMap<String, String> violationMessages) {		
		this.violationMessages = violationMessages;
	}

	public HashMap<String, String> getViolationMessages() {
		return violationMessages;
	}

}
