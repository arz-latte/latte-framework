package at.arz.latte.framework.admin.restapi;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.EntityManager;

import at.arz.latte.framework.admin.Group;
import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.admin.Permission;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.restapi.PermissionData;
import at.arz.latte.framework.util.Functions;
import at.arz.latte.framework.util.JPA;

/**
 * bean for group management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
class GroupEditor {

	private EntityManager em;

	public GroupEditor(EntityManager em) {
		this.em = Objects.requireNonNull(em);
	}

	public List<GroupData> getAllGroupsData() {
		return Functions.map(	AdminMapper.MAP_TO_GROUPDATA,
								new AdminQuery(em).allGroups());
	}

	public Group getGroup(Long groupId) {
		System.out.println("execute getGroup");
		Group r = em.find(Group.class, groupId);
		JPA.fetchAll(r.getPermissions());
		return r;
	}

	public Group createGroup(Group group, Set<PermissionData> permissionData) {

		checkDuplicateGroup(group.getId(), group.getName());

		em.persist(group);

		setGroupPermissions(group, permissionData);

		return group;
	}

	private void setGroupPermissions(	Group group,
										Set<PermissionData> permissionData) {
		for (PermissionData p : permissionData) {
			Permission permission = em.find(Permission.class, p.getId());
			group.addPermission(permission);
		}
	}

	/**
	 * update group via REST-service
	 * 
	 * @param id
	 * @param name
	 * @param permissionData
	 * @return
	 */
	public Group updateGroup(	Long id,
								String name,
								Set<PermissionData> permissionData) {

		checkDuplicateGroup(id, name);

		Group group = getGroup(id);
		group.setName(name);

		setGroupPermissions(group, permissionData);

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
	private void
			checkDuplicateGroup(Long groupId, String name) throws LatteValidationException {
		List<Group> duplicates = em.createNamedQuery(	Group.QUERY_GET_BY_NAME,
														Group.class)
									.setParameter("name", name)
									.getResultList();

		if (duplicates.size() == 1 && !duplicates.get(0)
													.getId()
													.equals(groupId)) {
			throw new LatteValidationException(	400,
												"name",
												"Eintrag bereits vorhanden");
		}
	}
}