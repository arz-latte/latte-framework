package at.arz.latte.framework.restapi.admin;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.framework.persistence.beans.GroupManagementBean;
import at.arz.latte.framework.persistence.models.Group;
import at.arz.latte.framework.persistence.models.Permission;
import at.arz.latte.framework.restful.dta.GroupData;
import at.arz.latte.framework.restful.dta.PermissionData;
import at.arz.latte.framework.services.restful.exception.LatteValidationException;

/**
 * RESTful service for group management
 * 
 * @author Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 * @author mrodler
 *
 */
@Stateless
@Path("/groups")
public class GroupAdministration {

	@Inject
	private GroupManagementBean bean;

	@Inject
	private Validator validator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<GroupData> getAllGroups() {
		return bean.getAllGroupsData();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData getRole(@PathParam("id") Long id) {
		Group group = bean.getGroup(id);
		return toGroupData(group);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData createRole(GroupData groupData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(groupData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Group group = new Group(groupData.getName());

		return toGroupData(bean.createGroup(group, groupData.getPermission()));
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public GroupData updateRole(GroupData groupData) {

		Set<ConstraintViolation<Object>> violations = requestValidation(groupData);
		if (!violations.isEmpty()) {
			throw new LatteValidationException(400, violations);
		}

		Group group = bean.updateGroup(groupData.getId(), groupData.getName(), groupData.getPermission());

		return toGroupData(group);
	}

	@DELETE
	@Path("{id}")
	public void deleteGroup(@PathParam("id") Long groupId) {
		bean.deleteGroup(groupId);
	}

	private Set<ConstraintViolation<Object>> requestValidation(Object groupData) {
		return validator.validate(groupData);
	}

	/**
	 * helper for REST service
	 * 
	 * @param group
	 * @return
	 */
	public GroupData toGroupData(Group group) {
		GroupData groupData = new GroupData();
		groupData.setId(group.getId());
		groupData.setName(group.getName());

		if (group.getPermissions() != null) {
			for (Permission permission : group.getPermissions()) {
				PermissionData permissionData = new PermissionData();
				permissionData.setId(permission.getId());
				groupData.addPermission(permissionData);
			}
		}
		
		return groupData;
	}
}