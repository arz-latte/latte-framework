package at.arz.latte.framework.authorization;

import java.util.Collections;
import java.util.Set;

import javax.enterprise.inject.Typed;

import at.arz.latte.framework.LatteUser;

/**
 * The implementation class of the CurrentUser interface.
 * 
 * @author mrodler
 *
 */
@Typed
class AnonymousUser implements LatteUser {
	private static final long serialVersionUID = 1L;
	private static final String UNAUTHENTICATED = "UNAUTHENTICATED";

	@Override
	public String getUserId() {
		return UNAUTHENTICATED;
	}

	@Override
	public boolean hasPermission(String permission) {
		return false;
	}

	@Override
	public Set<String> getAllPermissions() {
		return Collections.emptySet();
	}

	@Override
	public int hashCode() {
		return UNAUTHENTICATED.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AnonymousUser;
	}

	@Override
	public String toString() {
		return UNAUTHENTICATED;
	}

	@Override
	public boolean isInRole(String role) {
		return false;
	}

}
