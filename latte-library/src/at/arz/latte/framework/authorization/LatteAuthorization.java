package at.arz.latte.framework.authorization;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * AuthorizationService provides the CurrentUser Object, its Roles and
 * Permissions.
 * 
 * @author mrodler
 *
 */
@Stateless
public class LatteAuthorization {
	public static final String JPA_UNIT = "latte-library";

	@PersistenceContext(unitName = JPA_UNIT)
	private EntityManager em;

	@Resource
	private SessionContext sessionContext;

	private AuthorizationPermissionRepository permissions;

	public Principal getPrincipal() {
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		if (callerPrincipal == null) {
			return LattePrincipal.UNAUTHENTICATED;
		}
		return callerPrincipal;
	}

	@PostConstruct
	void afterBeanInitialization() {
		permissions = new AuthorizationPermissionRepository(em);
	}

	public boolean isUserInRole(String role) {
		return sessionContext.isCallerInRole(role);
	}

	private Set<String> readAllPermissions(Principal principal) {
		List<String> grantedPermissions = permissions.findGrantedPermissionsForUserId(principal.getName());
		return new HashSet<String>(grantedPermissions);
	}

	public Set<String> getPermissions() {
		Principal principal = sessionContext.getCallerPrincipal();
		return principal == null ? Collections.emptySet()
								: readAllPermissions(principal);
	}
}
