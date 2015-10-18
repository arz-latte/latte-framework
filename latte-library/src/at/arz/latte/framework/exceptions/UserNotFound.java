package at.arz.latte.framework.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class UserNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFound(String message) {
		super(message);
	}
}
