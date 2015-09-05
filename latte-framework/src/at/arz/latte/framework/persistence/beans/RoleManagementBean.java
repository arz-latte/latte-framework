package at.arz.latte.framework.persistence.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.Role;
import at.arz.latte.framework.restful.dta.PermissionData;
import at.arz.latte.framework.restful.dta.RoleData;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

/**
 * bean for role management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class RoleManagementBean {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	public List<RoleData> getAllRolesData() {
		return em.createNamedQuery(Role.QUERY_GETALL_BASE, RoleData.class).getResultList();
	}

	public List<PermissionData> getAllPermissionsData() {
		return em.createNamedQuery(Permission.QUERY_GETALL_BASE, PermissionData.class).getResultList();
	}

	public Role getRole(Long roleId) {
		Role r = em.find(Role.class, roleId);
		for (Permission p : r.getPermission()) {
			// fetch eager
		}
		return r;
	}

	public Role createRole(Role role, Set<PermissionData> permissionData) {

		checkDuplicateRole(role.getId(), role.getName());

		em.persist(role);

		Set<Permission> permissions = new HashSet<>();
		for (PermissionData p : permissionData) {
			Permission permission = em.find(Permission.class, p.getId());
			permissions.add(permission);
		}
		role.setPermission(permissions);

		return role;
	}

	/**
	 * update role via REST-service
	 * 
	 * @param id
	 * @param name
	 * @param permissionData
	 * @return
	 */
	public Role updateRole(Long id, String name, Set<PermissionData> permissionData) {

		checkDuplicateRole(id, name);

		Role role = getRole(id);
		role.setName(name);

		Set<Permission> permissions = new HashSet<>();
		for (PermissionData p : permissionData) {
			Permission permission = em.find(Permission.class, p.getId());
			permissions.add(permission);
		}
		role.setPermission(permissions);

		return role;
	}

	public void deleteRole(Long roleId) {
		Role toBeDeleted = getRole(roleId);
		em.remove(toBeDeleted);
	}

	/**
	 * check if role name is already stored, throws LatteValidationException if
	 * name is a duplicate
	 * 
	 * @param roleId
	 * @param name
	 * @throws LatteValidationException
	 */
	private void checkDuplicateRole(Long roleId, String name) throws LatteValidationException {
		List<Role> duplicates = em.createNamedQuery(Role.QUERY_GET_BY_NAME, Role.class).setParameter("name", name)
				.getResultList();

		if (duplicates.size() == 1 && !duplicates.get(0).getId().equals(roleId)) {
			throw new LatteValidationException(400, "name", "Eintrag bereits vorhanden");
		}
	}

}