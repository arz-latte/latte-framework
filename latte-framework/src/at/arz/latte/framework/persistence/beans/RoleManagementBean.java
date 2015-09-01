package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.Role;
import at.arz.latte.framework.restful.dta.PermissionData;
import at.arz.latte.framework.restful.dta.RoleData;

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

	public Role createRole(Role role) {
		em.persist(role);
		return role;
	}

	/**
	 * update role via REST-service
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public Role updateRole(Long id, String name) {
		Role role = getRole(id);
		role.setName(name);
		return role;
	}

	public void deleteRole(Long roleId) {
		Role toBeDeleted = getRole(roleId);
		em.remove(toBeDeleted);
	}

}