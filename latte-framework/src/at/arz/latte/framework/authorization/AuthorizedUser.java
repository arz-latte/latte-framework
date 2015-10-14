package at.arz.latte.framework.authorization;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import at.arz.latte.framework.LatteUser;

/**
 * The implementation class of the CurrentUser interface.
 * 
 * @author mrodler
 *
 */
class AuthorizedUser implements LatteUser {
	private static final long serialVersionUID = 1L;

	private LatteAuthorization authorization;
	private Set<String> permissions;

	private String userId;

	public AuthorizedUser(String userId, LatteAuthorization authorization) {
		Objects.requireNonNull(userId);
		Objects.requireNonNull(authorization);
		this.userId = userId;
		this.authorization = authorization;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public boolean hasPermission(String permission) {
		return getAllPermissions().contains(permission);
	}

	@Override
	public Set<String> getAllPermissions() {
		if (permissions == null) {
			permissions = authorization.getPermissions();
		}
		if (permissions == null) {
			permissions = Collections.emptySet();
		}
		return permissions;
	}

	@Override
	public int hashCode() {
		return userId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AuthorizedUser && equals((AuthorizedUser) obj);
	}

	public boolean equals(AuthorizedUser currentUser) {
		return currentUser != null && userId.equals(currentUser.userId);
	}

	@Override
	public String toString() {
		return userId;
	}

	@Override
	public boolean isInRole(String role) {
		return authorization.isUserInRole(role);
	}

}
