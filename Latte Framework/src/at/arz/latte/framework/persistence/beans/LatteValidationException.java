package at.arz.latte.framework.persistence.beans;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class LatteValidationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public LatteValidationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LatteValidationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LatteValidationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LatteValidationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LatteValidationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
