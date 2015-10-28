package at.arz.latte.framework.admin.restapi;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import at.arz.latte.framework.admin.AdminQuery;
import at.arz.latte.framework.admin.Group;
import at.arz.latte.framework.admin.Permission;
import at.arz.latte.framework.exceptions.LatteValidationException;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.restapi.PermissionData;

/**
 * bean for group management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
class GroupEditor {
	private static final Logger LOG = Logger.getLogger(GroupEditor.class.getSimpleName());

	private EntityManager em;

	public GroupEditor(EntityManager em) {
		this.em = Objects.requireNonNull(em);
	}

	public Group createGroup(GroupData groupData) {

		assertNameIsNotOwnedByAnotherGroup(groupData.getName());

		Group group = new Group(groupData.getName());
		em.persist(group);

		setGroupPermissions(group, groupData.getPermission());

		return group;
	}

	public Group updateGroup(GroupData groupData) {

		Group group = em.find(Group.class, groupData.getId());

		if (!Objects.equals(group.getName(), groupData.getName())) {
			assertNameIsNotOwnedByAnotherGroup(groupData.getName());
			group.setName(groupData.getName());
		}

		setGroupPermissions(group, groupData.getPermission());

		return group;
	}

	public void deleteGroup(Long id) {
		try {
			Group group = em.find(Group.class, id);
			em.remove(group);
			LOG.info("deleteGroup: group deleted:" + group.getName());
		} catch (EntityNotFoundException e) {
			LOG.info("deleteGroup: group not found:" + id);
		}
	}

	/**
	 * check if group name is already stored, throws LatteValidationException if
	 * name is a duplicate
	 * 
	 * @param name
	 * @throws LatteValidationException
	 */
	private	void
			assertNameIsNotOwnedByAnotherGroup(String name) throws LatteValidationException {

		TypedQuery<Group> query = new AdminQuery(em).groupByName(name);
		if (!query.getResultList().isEmpty()) {
			LOG.info("can't store group, name already exists:" + name);
			throw new LatteValidationException(	400,
												"name",
												"Eintrag bereits vorhanden");
		}
	}

	private void setGroupPermissions(	Group group,
										Set<PermissionData> permissionData) {
		for (PermissionData p : permissionData) {
			Permission permission = em.find(Permission.class, p.getId());
			group.addPermission(permission);
		}
	}

}