package at.arz.latte.framework.authorization;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import at.arz.latte.framework.LatteUser;
import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.restful.dta.PermissionData;

/**
 * AuthorizationService provides the CurrentUser Object, its Roles and
 * Permissions.
 * 
 * @author mrodler
 *
 */
@Stateless
public class LatteAuthorization {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	@Resource
	private SessionContext sessionContext;

	public boolean isUserInRole(String role) {
		return sessionContext.isCallerInRole(role);
	}

	private List<PermissionData> getAllPermissionsData() {
		TypedQuery<PermissionData> query = em.createNamedQuery(
				Permission.QUERY_GETALL_BASE, PermissionData.class);
		return query.getResultList();
	}

	@Produces
	@RequestScoped
	public LatteUser getCurrentUser() {
		Principal principal = sessionContext.getCallerPrincipal();
		if (principal == null) {
			return new AnonymousUser();
		}
		return new AuthorizedUser(principal.getName(), this);
	}

	public Set<String> getPermissions() {
		Set<String> permissions = new HashSet<String>();
		List<PermissionData> permissionData = getAllPermissionsData();
		for (PermissionData data : permissionData) {
			permissions.add(data.getName());
		}
		return permissions;
	}
}
