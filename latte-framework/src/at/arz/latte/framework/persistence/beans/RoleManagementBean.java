package at.arz.latte.framework.persistence.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Role;
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

	public List<Role> getAllRoles() {
		return em.createNamedQuery(Role.QUERY_GETALL, Role.class).getResultList();
	}

	public Role getRole(Long roleId) {
		return em.find(Role.class, roleId);
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