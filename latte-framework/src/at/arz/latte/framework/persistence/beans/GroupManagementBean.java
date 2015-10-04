package at.arz.latte.framework.persistence.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.persistence.models.Group;
import at.arz.latte.framework.restful.dta.PermissionData;
import at.arz.latte.framework.restful.dta.GroupData;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

/**
 * bean for group management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Stateless
public class GroupManagementBean {

	@PersistenceContext(unitName = "latte-unit")
	private EntityManager em;

	public List<GroupData> getAllGroupsData() {
		return em.createNamedQuery(Group.QUERY_GETALL_BASE, GroupData.class).getResultList();
	}

	public List<PermissionData> getAllPermissionsData() {
		return em.createNamedQuery(Permission.QUERY_GETALL_BASE, PermissionData.class).getResultList();
	}

	public Group getGroup(Long groupId) {
		Group r = em.find(Group.class, groupId);
		for (Permission p : r.getPermission()) {
			// fetch eager
		}
		return r;
	}

	public Group createGroup(Group group, Set<PermissionData> permissionData) {

		checkDuplicateGroup(group.getId(), group.getName());

		em.persist(group);

		Set<Permission> permissions = new HashSet<>();
		for (PermissionData p : permissionData) {
			Permission permission = em.find(Permission.class, p.getId());
			permissions.add(permission);
		}
		group.setPermission(permissions);

		return group;
	}

	/**
	 * update group via REST-service
	 * 
	 * @param id
	 * @param name
	 * @param permissionData
	 * @return
	 */
	public Group updateGroup(Long id, String name, Set<PermissionData> permissionData) {

		checkDuplicateGroup(id, name);

		Group group = getGroup(id);
		group.setName(name);

		Set<Permission> permissions = new HashSet<>();
		for (PermissionData p : permissionData) {
			Permission permission = em.find(Permission.class, p.getId());
			permissions.add(permission);
		}
		group.setPermission(permissions);

		return group;
	}

	public void deleteGroup(Long groupId) {
		Group toBeDeleted = getGroup(groupId);
		em.remove(toBeDeleted);
	}

	/**
	 * check if group name is already stored, throws LatteValidationException if
	 * name is a duplicate
	 * 
	 * @param groupId
	 * @param name
	 * @throws LatteValidationException
	 */
	private void checkDuplicateGroup(Long groupId, String name) throws LatteValidationException {
		List<Group> duplicates = em.createNamedQuery(Group.QUERY_GET_BY_NAME, Group.class).setParameter("name", name)
				.getResultList();

		if (duplicates.size() == 1 && !duplicates.get(0).getId().equals(groupId)) {
			throw new LatteValidationException(400, "name", "Eintrag bereits vorhanden");
		}
	}

}